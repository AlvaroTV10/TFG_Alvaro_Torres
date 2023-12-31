package genDataNOapplication.RV;

import genDataNOapplication.User.User;

import genDataNOapplication.model.ConfigurationModel;
import fakeNewsDetectorModel.TextClassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.IOException;

import genDataNOapplication.Publication.Publication;
import genDataNOapplication.RV.RV;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.regex.*;
import java.util.stream.Collectors;
import fakeNewsDetectorModel.TextClassifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class genPublicationsDV {
		

		public static void main(String arrg[], ConfigurationModel configuration) throws Exception {
			long timer = System.currentTimeMillis();
			
			List<Publication> publications = new LinkedList <Publication> ();
			List<String> topics_list = new LinkedList <String> ();
			List<String> political_orientations = new LinkedList <String> ();
			//List<String> emotions_list = new LinkedList <String> ();
			//Map<String, List<String>> publication_description_dict = new HashMap<String, List<String>>();
			Map<String, List<String>> topicDictionary = new HashMap<>();
			Map<String, String> newsDictionary = new HashMap<>();
			
			
			
			double time_rate, score1, score2, score3,  fakeNews_Score;
			int max_pub = configuration.getNumPublications(); //maximum of publications done in each time
			int eng_thres = configuration.getEngagementThreshold();
			int actual_num_pub, num_pub, random_user, user_id, friend_id1, friend_id2, friend_id3, interaction_d1, interaction_d2, interaction_d3, actual_id_pub=0;
			User actual_user, friend1, friend2, friend3;
			int num_users = RV.Users.size();
			int min_degree=1000000;
			int max_degree = 0;
			int time = configuration.getNumTime(); //units of time. The user has to decide it
			boolean fakenew;
			int news_count = 0, fakenews_count=0, detected_fakenews = 0, wrongly_detected_fakenews = 0, missed_fakenews = 0, detected_real = 0;
			
			//Fake News Detector model
			String modelPath = "./resources/Default_files/fakenewsmodel.model";
			// Create an object TextClassifier and load the model
            TextClassifier classifier = new TextClassifier(modelPath);
           
			System.out.println("YOU ARE IN THE DINAMIC VERSION");
			

			for (int i=0; i<num_users; i++) {
				random_user = (int) Math.round(Math.random() * (num_users-1));
				actual_user = (User)RV.Users.get(random_user);
				
				if(actual_user != null) {
					if (actual_user.friends.size()<min_degree) {
						min_degree= actual_user.friends.size();
					}
					if (actual_user.friends.size()>max_degree) {
						max_degree= actual_user.friends.size();
					}
				}
			}
			
			
			//publication_description_dict = set_dict_pub_desc(configuration);
			//topics_list = new ArrayList<String>(publication_description_dict.keySet());
			//Collections.sort(topics_list);
			topics_list = new ArrayList<>(Arrays.asList("Music_artist", "Drink", "TV_Show", "Sports", "Animals", "Computer_games", "News", "Food", "Travel", "Fashion", "Cars", "Jokes", "Technology", "Watch", "Jewellery", "Dance", "Physical_fitness", "Architecture", "Interior_design", "Culture"));
			for(int i=0; i<topics_list.size(); i++) {
				if (configuration.getTopicList().get(i)>0) {
					if (topics_list.get(i).equals("News")) {
						newsDictionary = generateNews();
					}else {
						List<String> descriptions_list = generate_pub_desc(topics_list.get(i));
						topicDictionary.put(topics_list.get(i), descriptions_list);
					}					
				}
			}

			//List of political orientations
			political_orientations = new ArrayList<>(Arrays.asList("Left", "Center", "Right"));	
			
			for(int t=0; t<time; t++) {
				actual_num_pub= (int) Math.round(Math.random() * max_pub);
				num_pub = actual_num_pub;
				time_rate = t/time;
				if (t>=20) {
					int extra_publi = max_pub - actual_num_pub;
					for (int i=0; i<500; i++) {
						if (extra_publi>0) {
							random_user = (int) Math.round(Math.random() * (num_users-1));
							actual_user = (User)RV.Users.get(random_user);
							if(actual_user != null) {
							double engage_val = evaluate_engagement(actual_user, publications);
							if (engage_val>=eng_thres) {
								//generate publication for actual user
								String type = decide_type(configuration.getImage(), configuration.getVideo(), configuration.getText());
								String topic = best_topic(actual_user, publications);
								String description;
						    	String political = "Center";
								Double quality = decide_quality();
								Boolean fakenews = false;
								
								user_id = actual_user.getUser();
								
								Publication publi = new Publication();
								publi.setId(actual_id_pub);
								publi.setDate(t);
								publi.loadUser(user_id);
								publi.loadType(type);
								publi.loadTopic(topic);
								Random random = new Random();
						    	String category = "real";
						    	String prediction = "real";
								if(topic.equals("News")) {
									description = getRandomNews(newsDictionary);
									publi.loadPublicationDescription(description);
									category = newsDictionary.get(description);
									news_count +=1;
									if(category.equals("fake")) {
										fakenews = true;
										fakenews_count += 1;
									}							
									political = political_orientations.get(random.nextInt(political_orientations.size()));
									
									//Fake new detection
									prediction = classifier.classifyText(publi.getPublicationDescription());
									
									if(prediction=="fake" && prediction==category) {
										detected_fakenews += 1;
										publi.setPrediction(true);
									}if(prediction=="fake" && prediction!=category) {
										wrongly_detected_fakenews +=1;
										publi.setPrediction(true);
									}if(prediction=="real" && prediction!=category) {
										missed_fakenews +=1;
										publi.setPrediction(false);
									}if(prediction=="real" && prediction==category) {
										detected_real +=1;
										publi.setPrediction(false);
									}
								}else {
									description = select_pub_desc(topicDictionary.get(topic));
									publi.loadPublicationDescription(description);
								}									
								publi.loadQuality(quality);	
								publi.setPoliticalOrientation(political);
								publi.setFakenew(fakenews);						

								actual_user.addPublication(actual_id_pub);
								publications.add(publi);
								
								int numPub = actual_user.getNumPublications();
								actual_user.setNumPublications(numPub +1);								
								tagUsers(publi, actual_user, configuration.getMaxTagged());
								for(int j=0; j<actual_user.friends.size();j++) {
									
									friend_id1 = actual_user.friends.get(j);
									friend1 = (User)RV.Users.get(friend_id1); //friend of distance 1
									
									if(friend1 != null) {
										score1= calculate_score_depth1(publi, friend1, actual_user, min_degree, max_degree, time_rate, publications, configuration);
										if (category=="fake") {
											fakeNews_Score = fakeNews_Score(publi, friend1);
											score1 = score1+fakeNews_Score;
											if(score1>1) {
												score1 = 1;
											}
										}
										interaction_d1= interaction_function(score1, configuration);
										List<Double> list1 = Arrays.asList(score1, Double.valueOf(interaction_d1));
										friend1.scores_given_d1.put(actual_id_pub, list1);
										
										if (interaction_d1 ==3) {
											friend1.addPublicationShared(actual_id_pub);
											publi.addUserShare(friend_id1);
											friend1.addPublicationLiked(actual_id_pub);
											publi.addUserLike(friend_id1);
											friend1.addPublicationCommented(actual_id_pub);
											publi.addUserComment(friend_id1);
											
											for(int k=0; k<friend1.friends.size(); k++) {
												friend_id2 = friend1.friends.get(k);
												friend2 = (User)RV.Users.get(friend_id2); //friend of distance 2
												if(friend2 != null) {
													if(publi.like_users.contains(friend2.getUser()) == false) {
														
														score2= calculate_score_depth2(publi, friend2, friend1, actual_user, min_degree, max_degree, time_rate, publications, configuration);
														if (category=="fake") {
															fakeNews_Score = fakeNews_Score(publi, friend2);
															score2 = score2+fakeNews_Score;
															if(score2>1) {
																score2 = 1;
															}	
														}
														interaction_d2= interaction_function(score2, configuration);
														List<Double> list2 = Arrays.asList(score2, Double.valueOf(interaction_d2));
														friend2.scores_given_d2.put(actual_id_pub, list2);
														
														if (interaction_d2==3) {
															friend2.addPublicationShared(actual_id_pub);
															publi.addUserShare(friend_id2);
															friend2.addPublicationLiked(actual_id_pub);
															publi.addUserLike(friend_id2);
															friend2.addPublicationCommented(actual_id_pub);
															publi.addUserComment(friend_id2);
																								
															for(int l=0; l<friend2.friends.size(); l++) {
																friend_id3 = friend2.friends.get(l);
																friend3 = (User)RV.Users.get(friend_id3); //friend of distance 3
																if(friend3 != null) {
																	if(publi.like_users.contains(friend3.getUser()) == false) {
																		score3= calculate_score_depth3(publi, friend3, friend2, friend1, actual_user, min_degree, max_degree, time_rate, publications, configuration);
																		if (category=="fake") {
																			fakeNews_Score = fakeNews_Score(publi, friend3);
																			score3 = score3+fakeNews_Score;
																			if(score3>1) {
																				score3 = 1;
																			}	
																		}
																		interaction_d3= interaction_function(score3, configuration);
																		List<Double> list3 = Arrays.asList(score3, Double.valueOf(interaction_d3));
																		friend3.scores_given_d3.put(actual_id_pub, list3);
																	if (interaction_d3==3) { //cannot share as they are on distance 3
																			friend3.addPublicationLiked(actual_id_pub);
																			publi.addUserLike(friend_id3);
																			friend3.addPublicationCommented(actual_id_pub);
																			publi.addUserComment(friend_id3);
																		}
																		else if (interaction_d3==2) {
																			friend3.addPublicationLiked(actual_id_pub);
																			publi.addUserLike(friend_id3);
																				friend3.addPublicationCommented(actual_id_pub);
																			publi.addUserComment(friend_id3);
																		}
																		else if (interaction_d3==1) {
																			friend3.addPublicationLiked(actual_id_pub);
																			publi.addUserLike(friend_id3);
																		}
																		else {
																			friend3.addPublicationIgnored(actual_id_pub);
																			publi.addUserIgnored(friend_id3);
																		}
																	}
																}
															}
														}
														else if (interaction_d2==2) {
															
															friend2.addPublicationLiked(actual_id_pub);
															publi.addUserLike(friend_id2);
															friend2.addPublicationCommented(actual_id_pub);
															publi.addUserComment(friend_id2);
															
														}
														else if (interaction_d2==1) {
															
															friend2.addPublicationLiked(actual_id_pub);
															publi.addUserLike(friend_id2);
														}
														else {
															friend2.addPublicationIgnored(actual_id_pub);
															publi.addUserIgnored(friend_id2);
														}
														
													}
												}
											}
										}
										else if (interaction_d1==2) {
											friend1.addPublicationLiked(actual_id_pub);
											publi.addUserLike(friend_id1);
											friend1.addPublicationCommented(actual_id_pub);
											publi.addUserComment(friend_id1);
										}
										else if (interaction_d1==1) {
											friend1.addPublicationLiked(actual_id_pub);
											publi.addUserLike(friend_id1);
										}
										else {
											friend1.addPublicationIgnored(actual_id_pub);
											publi.addUserIgnored(friend_id1);
										}
									}
								}
								actual_id_pub++;
								extra_publi -= 1;
								actual_num_pub ++;
							}
						}
						}
					}
				}
				RV.num_pub_by_time.put(t, actual_num_pub);
				System.out.println("There will be "+ actual_num_pub+" publications on time "+ t+"/"+time);
				for (int i=0; i<num_pub;i++) { 
					random_user = (int) Math.round(Math.random() * (num_users-1));
					actual_user = (User)RV.Users.get(random_user);
					if(actual_user != null) {
						//generate publication for actual user
						String type = decide_type(configuration.getImage(), configuration.getVideo(), configuration.getText());
						String topic;
				    	String description;
				    	String political = "Center";
						Double quality = decide_quality();
						Boolean fakenews = false;
						
						user_id = actual_user.getUser();
						
						Publication publi = new Publication();
						publi.setId(actual_id_pub);
						publi.setDate(t);
						publi.loadUser(user_id);
						publi.loadType(type);
						
						
						/*//may generate a fake new with probability 5%
						Random random = new Random();
					    if (random.nextInt(100) < 5) {
					    	topic = "News";
					    	description = generateFakeNews();
					    	political = political_orientations.get(random.nextInt(political_orientations.size()));
							publi.setFakenew(true);
							fakenews_count += 1;
					    }*/
						Random random = new Random();
				    	topic = decide_topic(topics_list, configuration.getTopicList());
				    	publi.loadTopic(topic);
				    	String category = "real";
				    	String prediction = "real";
				    	if(topic.equals("News")) {
							description = getRandomNews(newsDictionary);
							publi.loadPublicationDescription(description);
							category = newsDictionary.get(description);
							news_count +=1;
							if(category.equals("fake")) {
								fakenews = true;
								fakenews_count += 1;
							}							
							political = political_orientations.get(random.nextInt(political_orientations.size()));
							
							//Fake new detection
							prediction = classifier.classifyText(publi.getPublicationDescription());
							if(prediction=="fake" && prediction==category) {
								detected_fakenews += 1;
								publi.setPrediction(true);
							}if(prediction=="fake" && prediction!=category) {
								wrongly_detected_fakenews +=1;
								publi.setPrediction(true);
							}if(prediction=="real" && prediction!=category) {
								missed_fakenews +=1;
								publi.setPrediction(false);
							}if(prediction=="real" && prediction==category) {
								detected_real +=1;
								publi.setPrediction(false);
							}
						}else {
							description = select_pub_desc(topicDictionary.get(topic));
							publi.loadPublicationDescription(description);
						}									
						publi.loadQuality(quality);	
						publi.setPoliticalOrientation(political);
						publi.setFakenew(fakenews);						

						actual_user.addPublication(actual_id_pub);
						publications.add(publi);
						
						int numPub = actual_user.getNumPublications();
						actual_user.setNumPublications(numPub +1);
						tagUsers(publi, actual_user, configuration.getMaxTagged());
														    	
						for(int j=0; j<actual_user.friends.size();j++) {
							
							friend_id1 = actual_user.friends.get(j);
							friend1 = (User)RV.Users.get(friend_id1); //friend of distance 1
							
							if(friend1 != null) {
								score1= calculate_score_depth1(publi, friend1, actual_user, min_degree, max_degree, time_rate, publications, configuration);
								
								if (category=="fake") {
									fakeNews_Score = fakeNews_Score(publi, friend1);
									score1 = score1+fakeNews_Score;
									if(score1>1) {
										score1 = 1;
									}									
								}
								interaction_d1= interaction_function(score1, configuration);
								List<Double> list1 = Arrays.asList(score1, Double.valueOf(interaction_d1));
								friend1.scores_given_d1.put(actual_id_pub, list1);
								
								if (interaction_d1 ==3) {
									friend1.addPublicationShared(actual_id_pub);
									publi.addUserShare(friend_id1);
									friend1.addPublicationLiked(actual_id_pub);
									publi.addUserLike(friend_id1);
									friend1.addPublicationCommented(actual_id_pub);
									publi.addUserComment(friend_id1);
									
									for(int k=0; k<friend1.friends.size(); k++) {
										friend_id2 = friend1.friends.get(k);
										friend2 = (User)RV.Users.get(friend_id2); //friend of distance 2
										if(friend2 != null) {
											if(publi.like_users.contains(friend2.getUser()) == false) {
												
												score2= calculate_score_depth2(publi, friend2, friend1, actual_user, min_degree, max_degree, time_rate, publications, configuration);
												if (category=="fake") {
													fakeNews_Score = fakeNews_Score(publi, friend2);
													score2 = score2+fakeNews_Score;
													if(score2>1) {
														score2 = 1;
													}	
												}
												interaction_d2= interaction_function(score2, configuration);
												List<Double> list2 = Arrays.asList(score2, Double.valueOf(interaction_d2));
												friend2.scores_given_d2.put(actual_id_pub, list2);
												
												if (interaction_d2==3) {
													friend2.addPublicationShared(actual_id_pub);
													publi.addUserShare(friend_id2);
													friend2.addPublicationLiked(actual_id_pub);
													publi.addUserLike(friend_id2);
													friend2.addPublicationCommented(actual_id_pub);
													publi.addUserComment(friend_id2);
																						
													for(int l=0; l<friend2.friends.size(); l++) {
														friend_id3 = friend2.friends.get(l);
														friend3 = (User)RV.Users.get(friend_id3); //friend of distance 3
														if(friend3 != null) {
															if(publi.like_users.contains(friend3.getUser()) == false) {
																score3= calculate_score_depth3(publi, friend3, friend2, friend1, actual_user, min_degree, max_degree, time_rate, publications, configuration);
																if (category=="fake") {
																	fakeNews_Score = fakeNews_Score(publi, friend3);
																	score3 = score3+fakeNews_Score;
																	if(score3>1) {
																		score3 = 1;
																	}	
																}
																interaction_d3= interaction_function(score3, configuration);
																List<Double> list3 = Arrays.asList(score3, Double.valueOf(interaction_d3));
																friend3.scores_given_d3.put(actual_id_pub, list3);
															if (interaction_d3==3) { //cannot share as they are on distance 3
																	friend3.addPublicationLiked(actual_id_pub);
																	publi.addUserLike(friend_id3);
																	friend3.addPublicationCommented(actual_id_pub);
																	publi.addUserComment(friend_id3);
																}
																else if (interaction_d3==2) {
																	friend3.addPublicationLiked(actual_id_pub);
																	publi.addUserLike(friend_id3);
																		friend3.addPublicationCommented(actual_id_pub);
																	publi.addUserComment(friend_id3);
																}
																else if (interaction_d3==1) {
																	friend3.addPublicationLiked(actual_id_pub);
																	publi.addUserLike(friend_id3);
																}
																else {
																	friend3.addPublicationIgnored(actual_id_pub);
																	publi.addUserIgnored(friend_id3);
																}
															}
														}
													}
												}
												else if (interaction_d2==2) {
													
													friend2.addPublicationLiked(actual_id_pub);
													publi.addUserLike(friend_id2);
													friend2.addPublicationCommented(actual_id_pub);
													publi.addUserComment(friend_id2);
													
												}
												else if (interaction_d2==1) {
													
													friend2.addPublicationLiked(actual_id_pub);
													publi.addUserLike(friend_id2);
												}
												else {
													friend2.addPublicationIgnored(actual_id_pub);
													publi.addUserIgnored(friend_id2);
												}
												
											}
										}
									}
								}
								else if (interaction_d1==2) {
									friend1.addPublicationLiked(actual_id_pub);
									publi.addUserLike(friend_id1);
									friend1.addPublicationCommented(actual_id_pub);
									publi.addUserComment(friend_id1);
								}
								else if (interaction_d1==1) {
									friend1.addPublicationLiked(actual_id_pub);
									publi.addUserLike(friend_id1);
								}
								else {
									friend1.addPublicationIgnored(actual_id_pub);
									publi.addUserIgnored(friend_id1);
								}
							}
						}
						actual_id_pub++;
				}		
			}
			
		}
		System.out.println("\n ---- FAKE NEWS STATISTICS ---- \n");
		System.out.println(detected_fakenews + " Fake News DETECTED out of " + fakenews_count);
		System.out.println(missed_fakenews + " Fake News MISSED out of " + fakenews_count);
		System.out.println((detected_fakenews+detected_real) + " RIGHTLY predicted news out of " + news_count);
		System.out.println(wrongly_detected_fakenews + " WRONGLY detected fakenews ");
		System.out.println("\n ------------------------------ \n");
		
			
		RV.publications_RV.addAll(publications);
		
		writeUSerPublications(configuration.getDin1());
		writeScoreGiven(configuration.getDin2());
		writeinfoPublications(publications, configuration.getDin3());
		writenumfriends_numpub(configuration.getDin4());
		writeUsersTagged(publications, configuration.getDin5());
		writeUserInteractions(configuration.getDin6());
		
		System.out.println("Run took " + (System.currentTimeMillis()-timer) * 0.001 + " secs");
		}	
		public static void publication_propagation(Publication publication, User user) {
			
		}
		
		public static double calculate_score_depth1(Publication publication, User friend, User user_pub, int min_degree, int max_degree, double time_rate, List<Publication> publications, ConfigurationModel configuration) {
			if(configuration.getModeAM() == 0) {
				double degree, weight, quality, type, degree_rate, weight_rate, quality_rate, type_rate, relation_rate, score, alpha, mu, relation_oldpub_user, relation_oldpub_user_rate, relation_topic_oldpub, relation_topic_oldpub_rate;
				double beta = 0.15 - 0.15*time_rate;
				double tagged_rate = 0.15;
				int relation_pub_user, tagged;
				String age;
				degree = degree_norm(friend, min_degree, max_degree);
				weight = user_pub.getWeight_friend(friend.getUser());
				quality = publication.getQuality();
				type = type_weight(publication, configuration);
				relation_pub_user= relation_friend_pub(friend, publication);
				age = friend.getAge();
				tagged = tagged_or_not(friend, publication);
				
				if (age=="18-25"){
					alpha=0.35-0.35*time_rate;
					mu=0.35-0.35*time_rate;
					degree_rate = beta;
					weight_rate = mu;
					relation_oldpub_user_rate = 0.5-beta-mu;
					quality_rate = 0.05;
					type_rate = 0.1;
					relation_rate = alpha;
					relation_topic_oldpub_rate =0.35-alpha;
				}
				else if(age=="26-35" || age=="36-45") {
					alpha=0.45-0.45*time_rate;
					mu=0.25-0.25*time_rate;
					degree_rate = beta;
					weight_rate = mu;
					relation_oldpub_user_rate = 0.4-beta-mu;
					quality_rate = 0.1;
					type_rate = 0.05;
					relation_rate = alpha;
					relation_topic_oldpub_rate =0.45-alpha;
				}
				else {
					alpha=0.5-0.5*time_rate;
					mu=0.2-0.2*time_rate;
					degree_rate = beta;
					weight_rate = mu;
					relation_oldpub_user_rate = 0.35-beta-mu;
					quality_rate = 0.14;
					type_rate = 0.01;
					relation_rate = alpha;
					relation_topic_oldpub_rate =0.5-alpha;
				}
				
				relation_oldpub_user = calculate_relation_oldpub_user(friend, user_pub, publications);
				relation_topic_oldpub = calculate_relation_topic_oldpub(friend, publications, publication);
				score = degree_rate*degree+weight_rate*weight+relation_oldpub_user_rate*relation_oldpub_user+quality_rate*quality+type_rate*type+relation_rate*relation_pub_user+relation_topic_oldpub_rate*relation_topic_oldpub+tagged_rate*tagged;
				return score;
			}
		
			else {
				return calculate_score_depth1_manual(publication, friend, user_pub, min_degree, max_degree, time_rate, publications, configuration);	
			}
		}
			
			public static double calculate_score_depth1_manual(Publication publication, User friend, User user_pub, int min_degree, int max_degree, double time_rate, List<Publication> publications, ConfigurationModel configuration) {
				double degree, weight, quality, type, degree_rate, weight_rate, quality_rate, type_rate, relation_rate, score, alpha, mu, relation_oldpub_user, relation_oldpub_user_rate, relation_topic_oldpub, relation_topic_oldpub_rate;
				double beta = configuration.getBeta() - configuration.getBeta()*time_rate;
				double tagged_rate = configuration.getMaxTagged();
				int relation_pub_user, tagged;
				
				degree = degree_norm(friend, min_degree, max_degree);
				weight = user_pub.getWeight_friend(friend.getUser());
				quality = publication.getQuality();
				type = type_weight(publication, configuration);
				relation_pub_user= relation_friend_pub(friend, publication);
				tagged = tagged_or_not(friend, publication);
				
				alpha=configuration.getAlpha()-configuration.getAlpha()*time_rate;
				mu=configuration.getMu()-configuration.getMu()*time_rate;
				degree_rate = beta;
				weight_rate = mu;
				relation_oldpub_user_rate = (configuration.getBeta()+ configuration.getMu())-beta-mu;
				quality_rate = configuration.getQualRate();
				type_rate = configuration.getTypeRate();
				relation_rate = alpha;
				relation_topic_oldpub_rate = configuration.getAlpha()-alpha;
				
				relation_oldpub_user = calculate_relation_oldpub_user(friend, user_pub, publications);
				relation_topic_oldpub = calculate_relation_topic_oldpub(friend, publications, publication);
				score = degree_rate*degree+weight_rate*weight+relation_oldpub_user_rate*relation_oldpub_user+quality_rate*quality+type_rate*type+relation_rate*relation_pub_user+relation_topic_oldpub_rate*relation_topic_oldpub+tagged_rate*tagged;
				return score;
				
			}
		
		public static double calculate_score_depth2(Publication publication, User friend2, User user_share, User user_pub, int min_degree, int max_degree, double time_rate, List<Publication> publications, ConfigurationModel configuration) {
			double pen2 = configuration.getPenalty2();
			if(friend2.publications_ignored.contains(publication.getId()) && friend2.scores_given_d1.containsKey(publication.getId())) { //see if has the possibility of interaction at distance 1
				return calculate_score_depth2_yes(publication, friend2, user_share, user_pub, min_degree, max_degree, time_rate, publications, pen2);
			}
			else {
				if(configuration.getModeAM()==0) {
					return calculate_score_depth2_no(publication, friend2, user_share, user_pub, min_degree, max_degree, time_rate, publications, pen2, configuration);
				}
				else {
					return calculate_score_depth2_no_manual(publication, friend2, user_share, user_pub, min_degree, max_degree, time_rate, publications, configuration, pen2);
				}
			}
		}
		
		public static double calculate_score_depth2_no(Publication publication, User friend2, User user_share, User user_pub, int min_degree, int max_degree, double time_rate, List<Publication> publications, double pen2, ConfigurationModel configuration) {
			double degree, weight, quality, type, degree_rate, weight_rate, quality_rate, type_rate, relation_rate, score, alpha, mu, relation_oldpub_user, relation_oldpub_user_rate, relation_topic_oldpub, relation_topic_oldpub_rate, relation_oldshared_user, relation_oldshared_user_rate;
			double beta = 0.15-0.15*time_rate;
			double penalty = pen2; //probando
			int relation_pub_user;
			String age;
			degree = degree_norm(friend2, min_degree, max_degree);
			weight = user_share.getWeight_friend(friend2.getUser());
			quality = publication.getQuality();
			type = type_weight(publication, configuration);
			relation_pub_user= relation_friend_pub(friend2, publication);
			age = friend2.getAge();
			
			
			if (age=="18-25"){
				alpha=0.35-0.35*time_rate;
				mu=0.35-0.35*time_rate;
				degree_rate = beta;
				weight_rate = mu;
				relation_oldpub_user_rate = relation_oldshared_user_rate = (0.5-beta-mu)*0.5;
				quality_rate = 0.05;
				type_rate = 0.1;
				relation_rate = alpha;
				relation_topic_oldpub_rate =0.35-alpha;
			}
			else if(age=="26-35" || age=="36-45") {
				alpha=0.45-0.45*time_rate;
				mu=0.25-0.25*time_rate;
				degree_rate = beta;
				weight_rate = mu;
				relation_oldpub_user_rate = relation_oldshared_user_rate = (0.4-beta-mu)*0.5;
				quality_rate = 0.1;
				type_rate = 0.05;
				relation_rate = alpha;
				relation_topic_oldpub_rate =0.45-alpha;
			}
			else {
				alpha=0.5-0.5*time_rate;
				mu=0.2-0.2*time_rate;
				degree_rate = beta;
				weight_rate = mu;
				relation_oldpub_user_rate = relation_oldshared_user_rate = (0.35-beta-mu)*0.5;
				quality_rate = 0.14;
				type_rate = 0.01;
				relation_rate = alpha;
				relation_topic_oldpub_rate =0.5-alpha;
			}
			
			relation_oldpub_user = calculate_relation_oldpub_user(friend2, user_pub, publications);
			relation_topic_oldpub = calculate_relation_topic_oldpub(friend2, publications, publication);
			relation_oldshared_user = calculate_relation_oldshared_user(friend2, user_share, publications);
			score = degree_rate*degree+weight_rate*weight+relation_oldpub_user_rate*relation_oldpub_user+relation_oldshared_user_rate*relation_oldshared_user+quality_rate*quality+type_rate*type+relation_rate*relation_pub_user+relation_topic_oldpub_rate*relation_topic_oldpub-penalty;
			return score;
		}
		
		public static double calculate_score_depth2_no_manual(Publication publication, User friend2, User user_share, User user_pub, int min_degree, int max_degree, double time_rate, List<Publication> publications, ConfigurationModel configuration, double pen2) {
			double degree, weight, quality, type, degree_rate, weight_rate, quality_rate, type_rate, relation_rate, score, alpha, mu, relation_oldpub_user, relation_oldpub_user_rate, relation_topic_oldpub, relation_topic_oldpub_rate, relation_oldshared_user, relation_oldshared_user_rate;
			double beta = configuration.getBeta()-configuration.getBeta()*time_rate;
			double penalty = pen2;
			int relation_pub_user;
			degree = degree_norm(friend2, min_degree, max_degree);
			weight = user_share.getWeight_friend(friend2.getUser());
			quality = publication.getQuality();
			type = type_weight(publication, configuration);
			relation_pub_user= relation_friend_pub(friend2, publication);
			
			alpha=configuration.getAlpha()-configuration.getAlpha()*time_rate;
			mu=configuration.getMu()-configuration.getMu()*time_rate;
			degree_rate = beta;
			weight_rate = mu;
			relation_oldpub_user_rate = relation_oldshared_user_rate = ((configuration.getBeta()+configuration.getMu())-beta-mu)*0.5;
			quality_rate = configuration.getQualRate();
			type_rate = configuration.getTypeRate();
			relation_rate = alpha;
			relation_topic_oldpub_rate = configuration.getAlpha()-alpha;
			
			
			relation_oldpub_user = calculate_relation_oldpub_user(friend2, user_pub, publications);
			relation_topic_oldpub = calculate_relation_topic_oldpub(friend2, publications, publication);
			relation_oldshared_user = calculate_relation_oldshared_user(friend2, user_share, publications);
			score = degree_rate*degree+weight_rate*weight+relation_oldpub_user_rate*relation_oldpub_user+relation_oldshared_user_rate*relation_oldshared_user+quality_rate*quality+type_rate*type+relation_rate*relation_pub_user+relation_topic_oldpub_rate*relation_topic_oldpub-penalty;
			return score;
		}
		
		public static double calculate_score_depth2_yes(Publication publication, User friend2, User user_share, User user_pub, int min_degree, int max_degree, double time_rate, List<Publication> publications, double pen2) {
			int publication_id = publication.getId();
			double weight = user_share.getWeight_friend(friend2.getUser());
			double score_d1_rate = 0.7;
			double weight_rate=0.1;
			double relation_oldshared_user_rate=0.2;
			double penalty = pen2;
			double relation_oldshared_user = calculate_relation_oldshared_user(friend2, user_share, publications);
			
			double score_d1 = friend2.scores_given_d1.get(publication_id).get(0); 
			double score = score_d1_rate*score_d1+weight_rate*weight+relation_oldshared_user_rate*relation_oldshared_user-penalty;
			return score;
		}
		
		
		
		public static double calculate_score_depth3(Publication publication, User friend3, User user_share, User friend1, User user_pub, int min_degree, int max_degree, double time_rate, List<Publication> publications, ConfigurationModel configuration) {
			double pen3 = configuration.getPenalty3();
			if(friend3.scores_given_d1.containsKey(publication.getId()) && friend3.scores_given_d2.containsKey(publication.getId())) { //friend 3 had the oportunity of interaction with distance 1 and 2 before now.
				return calculate_score_depth3_1_and_2(publication, friend3, user_share, friend1, user_pub, publications, pen3);
			}
			else if(friend3.scores_given_d1.containsKey(publication.getId()) || friend3.scores_given_d2.containsKey(publication.getId())) {//friend 3 had the oportunity of interaction with distance 1 or 2 before now.
				return calculate_score_depth3_1_or_2(publication, friend3, user_share, friend1, user_pub, publications, pen3);
			}
			else { //friend 3 is the first time that see the publication
				if (configuration.getModeAM()==0) {
					return calculate_score_depth3_no(publication, friend3, user_share, friend1, user_pub, min_degree, max_degree, time_rate, publications, pen3, configuration);
				}
				else {
					return calculate_score_depth3_no_manual(publication, friend3, user_share, friend1, user_pub, min_degree, max_degree, time_rate, publications, configuration, pen3);
				}
			}
		}
		
		
		public static double calculate_score_depth3_no(Publication publication, User friend3, User user_share, User friend1, User user_pub, int min_degree, int max_degree, double time_rate, List<Publication> publications, double pen3, ConfigurationModel configuration) {
			double degree, weight, quality, type, degree_rate, weight_rate, quality_rate, type_rate, relation_rate, score, alpha, mu, relation_oldpub_user, relation_oldpub_user_rate, relation_topic_oldpub, relation_topic_oldpub_rate, relation_oldshared_user, relation_oldshared_user_rate;
			double beta = 0.15-0.15*time_rate;
			double penalty = pen3;
			int relation_pub_user;
			String age;
			degree = degree_norm(friend3, min_degree, max_degree);
			weight = user_share.getWeight_friend(friend3.getUser());
			quality = publication.getQuality();
			type = type_weight(publication, configuration);
			relation_pub_user= relation_friend_pub(friend3, publication);
			age = friend3.getAge();
			
			
			if (age=="18-25"){
				alpha=0.35-0.35*time_rate;
				mu=0-35-0.35*time_rate;
				degree_rate = beta;
				weight_rate = mu;
				relation_oldpub_user_rate = relation_oldshared_user_rate = (0.5-beta-mu)*0.5;
				quality_rate = 0.05;
				type_rate = 0.1;
				relation_rate = alpha;
				relation_topic_oldpub_rate =0.35-alpha;
			}
			else if(age=="26-35" || age=="36-45") {
				alpha=0.45-0.45*time_rate;
				mu=0.25-0.25*time_rate;
				degree_rate = beta;
				weight_rate = mu;
				relation_oldpub_user_rate = relation_oldshared_user_rate = (0.4-beta-mu)*0.5;
				quality_rate = 0.1;
				type_rate = 0.05;
				relation_rate = alpha;
				relation_topic_oldpub_rate =0.45-alpha;
			}
			else {
				alpha=0.5-0.5*time_rate;
				mu=0.2-0.2*time_rate;
				degree_rate = beta;
				weight_rate = mu;
				relation_oldpub_user_rate = relation_oldshared_user_rate = (0.35-beta-mu)*0.5;
				quality_rate = 0.14;
				type_rate = 0.01;
				relation_rate = alpha;
				relation_topic_oldpub_rate =0.5-alpha;
			}
			
			relation_oldpub_user = calculate_relation_oldpub_user(friend3, user_pub, publications);
			relation_topic_oldpub = calculate_relation_topic_oldpub(friend3, publications, publication);
			relation_oldshared_user = calculate_relation_oldshared_user(friend3, user_share, publications);
			score = degree_rate*degree+weight_rate*weight+relation_oldpub_user_rate*relation_oldpub_user+relation_oldshared_user_rate*relation_oldshared_user+quality_rate*quality+type_rate*type+relation_rate*relation_pub_user+relation_topic_oldpub_rate*relation_topic_oldpub-penalty;
			return score;
		
		}
		
		public static double calculate_score_depth3_no_manual(Publication publication, User friend3, User user_share, User friend1, User user_pub, int min_degree, int max_degree, double time_rate, List<Publication> publications, ConfigurationModel configuration, double pen3) {
			double degree, weight, quality, type, degree_rate, weight_rate, quality_rate, type_rate, relation_rate, score, alpha, mu, relation_oldpub_user, relation_oldpub_user_rate, relation_topic_oldpub, relation_topic_oldpub_rate, relation_oldshared_user, relation_oldshared_user_rate;
			double beta = configuration.getBeta()-configuration.getBeta()*time_rate;
			double penalty = pen3;
			int relation_pub_user;
			degree = degree_norm(friend3, min_degree, max_degree);
			weight = user_share.getWeight_friend(friend3.getUser());
			quality = publication.getQuality();
			type = type_weight(publication, configuration);
			relation_pub_user= relation_friend_pub(friend3, publication);
			
			alpha=configuration.getAlpha()-configuration.getAlpha()*time_rate;
			mu=configuration.getMu()-configuration.getMu()*time_rate;
			degree_rate = beta;
			weight_rate = mu;
			relation_oldpub_user_rate = relation_oldshared_user_rate = ((configuration.getBeta()+configuration.getMu())-beta-mu)*0.5;
			quality_rate = configuration.getQualRate();
			type_rate = configuration.getTypeRate();
			relation_rate = alpha;
			relation_topic_oldpub_rate = configuration.getAlpha()-alpha;
			
			relation_oldpub_user = calculate_relation_oldpub_user(friend3, user_pub, publications);
			relation_topic_oldpub = calculate_relation_topic_oldpub(friend3, publications, publication);
			relation_oldshared_user = calculate_relation_oldshared_user(friend3, user_share, publications);
			score = degree_rate*degree+weight_rate*weight+relation_oldpub_user_rate*relation_oldpub_user+relation_oldshared_user_rate*relation_oldshared_user+quality_rate*quality+type_rate*type+relation_rate*relation_pub_user+relation_topic_oldpub_rate*relation_topic_oldpub-penalty;
			return score;
		}
		
		public static double calculate_score_depth3_1_and_2(Publication publication, User friend3, User user_share, User friend1, User user_pub, List<Publication> publications, double pen3) {
			int publication_id = publication.getId();
			double weight = user_share.getWeight_friend(friend3.getUser());
			double score_d1_rate = 0.6;
			double score_d2_rate = 0.25;
			double weight_rate=0.05;
			double relation_oldshared_user_rate=0.1;
			double penalty = pen3;
			double relation_oldshared_user = calculate_relation_oldshared_user(friend3, user_share, publications);
			double score_d1 = friend3.scores_given_d1.get(publication_id).get(0); 
			double score_d2 = friend3.scores_given_d2.get(publication_id).get(0);
	
			double score = score_d1_rate*score_d1+score_d2_rate*score_d2+weight_rate*weight+relation_oldshared_user_rate*relation_oldshared_user-penalty;
			return score;
		}
		
		public static double calculate_score_depth3_1_or_2(Publication publication, User friend3, User user_share, User friend1, User user_pub, List<Publication> publications, double pen3) {
			int publication_id = publication.getId();
			double weight = user_share.getWeight_friend(friend3.getUser());
			double score_di_rate = 0.7;
			double weight_rate=0.1;
			double relation_oldshared_user_rate=0.2;
			double penalty = pen3;
			double relation_oldshared_user = calculate_relation_oldshared_user(friend3, user_share, publications);
			double score_di=0;
			if(friend3.scores_given_d2.containsKey(publication.getId())) {	
				score_di = friend3.scores_given_d2.get(publication_id).get(0); 
			}
			else {
				score_di = friend3.scores_given_d1.get(publication_id).get(0);
				
			}
			
			double score = score_di_rate*score_di+weight_rate*weight+relation_oldshared_user_rate*relation_oldshared_user-penalty;
			return score;
		}
		
		public static double calculate_relation_oldpub_user(User friend, User user_pub, List<Publication> publications) {
			int friend_id = friend.getUser();
			int num_interactions = 0;
			Publication pub;
			int pub_id;
			for(int i=0; i<user_pub.publications.size(); i++) {
				pub_id = user_pub.publications.get(i);
				pub = publications.get(pub_id);
				if (pub.like_users.contains(friend_id)) {
					num_interactions ++;
				}
			}
			int total_publications_user = user_pub.publications.size();
			
			if (total_publications_user ==0) {
				return 0;
			}
			else {
				return num_interactions/total_publications_user;
			}
		}
		
		public static double calculate_relation_oldshared_user(User friend, User user_shared, List<Publication> publications) {
			int friend_id = friend.getUser();
			int num_interactions = 0;
			Publication pub;
			int pub_id;
			for(int i=0; i<user_shared.publications_shared.size(); i++) {
				pub_id = user_shared.publications_shared.get(i);
				pub = publications.get(pub_id);
				if (pub.like_users.contains(friend_id)) {
					num_interactions ++;
				}
			}
			int total_publications_shared = user_shared.publications_shared.size();
			
			if (total_publications_shared ==0) {
				return 0;
			}
			else {
				return num_interactions/total_publications_shared;
			}
			
			
		}
		
		public static double calculate_relation_topic_oldpub(User friend, List<Publication> publications, Publication actual_pub) {
			int pub_id;
			Publication pub;
			int num_interactions_topic = 0;
			for (int i=0; i<friend.publications_liked.size(); i++) {
				pub_id = friend.publications_liked.get(i);
				pub = publications.get(pub_id);
				if(pub.getTopic() == actual_pub.getTopic()) {
					num_interactions_topic ++;
				}
				
			}
			int total_pubs_topic = num_interactions_topic;
			for(int i=0; i<friend.publications_ignored.size();i++) {
				pub_id = friend.publications_ignored.get(i);
				pub = publications.get(pub_id);
				if(pub.getTopic() == actual_pub.getTopic()) {
					total_pubs_topic ++;
				}
			}
			
			if (total_pubs_topic ==0) {
				return 0;
			}
			else {
			return num_interactions_topic/total_pubs_topic;
			}
		}
		
		public static int interaction_function(double score, ConfigurationModel config) {
			double num_random = Math.random();
			if(score>=num_random && score>config.getMinScore()) {
				if(score-config.getSharePenalty()>=num_random) {
					return 3;}
				else if(score-config.getCommmentPenalty()>=num_random) {
					return 2;}
				else {
					return 1;}
			}
			else {
				return 0;}
		}
		
		public static double degree_norm(User user, int min_degree, int max_degree) {
			return 1 - ((double)(user.friends.size()-min_degree)/(double)(max_degree-min_degree)); //pillar min i max
		}
		
		public static double type_weight(Publication publication, ConfigurationModel config) {
			if (publication.getType() == "Image") {
				return config.getImageWeight();
			}
			else if(publication.getType() == "Video") {
				return config.getVideoWeight();
			}
			else {return config.getTextWeight();}
		}
		
		public static int relation_friend_pub(User user, Publication publication) {
			if (user.getLike(1)==publication.getTopic() || user.getLike(2)==publication.getTopic() || user.getLike(3)==publication.getTopic()) {
				return 1;
			}
			else {return 0;}
		}
		
		public static String decide_type(double image_rate, double video_rate, double text_rate) {
			double num_random = Math.random();
			if(num_random <image_rate) {return "Image";}
			else if(num_random<image_rate+video_rate) {return "Video";}
			else {return "Text";}
		}
		
		
		
		//By Álvaro
		public static List<String> generate_pub_desc(String topic) throws IOException {
	        List<String> posts = new ArrayList<>();
	        String wikipediaUrl = "https://en.wikipedia.org/wiki/" + topic;
	        Document doc = Jsoup.connect(wikipediaUrl).get();
	        
	        for(Element caption : doc.select(".thumbcaption")){       	 
	        	 String text = caption.text();
	        	 //Remove elements between square brackets
	        	 String regex = "\\[([^\\]]*)\\]";
	        	 Pattern pattern = Pattern.compile(regex);
	        	 Matcher matcher = pattern.matcher(text);
	        	 while (matcher.find()) {
	        	     text = text.replace(matcher.group(), "");
	        	 }
	        	//Remove accent signs (diacritical marks) 
	        	 String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);
	        	 Pattern pattern2 = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	        	 text = pattern2.matcher(normalizedText).replaceAll("");
	        	 
	        	 
	        	 //Replace ";" with "," to avoid problems with doc
	        	 text = text.replace(";", ",");
	        	 
	        	 posts.add(text);
		    	}
	       
	        //Retrieve links to subpages from the topic's Wikipedia page
	        Elements links = doc.select("#bodyContent a[href^='/wiki/']");
	        List<String> subpageUrls = links.stream().map(link -> link.absUrl("href")).collect(Collectors.toList());
	        
	        Random rand = new Random();
	        for (int i = 0; i < 10 && i < subpageUrls.size(); i++) {
	            String subpageUrl = subpageUrls.get(rand.nextInt(subpageUrls.size()));
	            Document subpageDoc = Jsoup.connect(subpageUrl).get();
	            for(Element caption : subpageDoc.select(".thumbcaption")){	        	 
		        	 String text = caption.text();
		        	 //Remove elements between square brackets
		        	 String regex = "\\[([^\\]]*)\\]";
		        	 Pattern pattern = Pattern.compile(regex);
		        	 Matcher matcher = pattern.matcher(text);
		        	 while (matcher.find()) {
		        	     text = text.replace(matcher.group(), "");
		        	 }
		        	 //Remove accent signs (diacritical marks) 
		        	 String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);
		        	 Pattern pattern2 = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		        	 text = pattern2.matcher(normalizedText).replaceAll("");
		        	 		        	 
		        	//Replace ";" with "," to avoid problems with doc
		        	 text = text.replace(";", ",");
		        	 
		        	 posts.add(text);		        	 
	            }
	        }
	      
	        
	        System.out.printf("Different snippets created for %s: %d \n", topic, posts.size());
	        return posts;
	    }
		
		public static String select_pub_desc(List<String> posts) throws IOException {
			Random rand = new Random();
			rand = new Random();
	        int randomIndex = rand.nextInt(posts.size());
	        return posts.get(randomIndex);
		}
				
		
		public static String decide_topic(List<String> topics_list, List<Double> topics_rate) {
			double randomGenerator = Math.random();
			if (randomGenerator  < topics_rate.get(0)) {
				return topics_list.get(0);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)){
				return topics_list.get(1);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)){
				return topics_list.get(2);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)){
				return topics_list.get(3);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)){
				return topics_list.get(4);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)){
				return topics_list.get(5);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)){
				return topics_list.get(6);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)){
				return topics_list.get(7);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)+topics_rate.get(8)){
				return topics_list.get(8);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)+topics_rate.get(8)+topics_rate.get(9)){
				return topics_list.get(9);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)+topics_rate.get(8)+topics_rate.get(9)+topics_rate.get(10)){
				return topics_list.get(10);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)+topics_rate.get(8)+topics_rate.get(9)+topics_rate.get(10)+topics_rate.get(11)){
				return topics_list.get(11);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)+topics_rate.get(8)+topics_rate.get(9)+topics_rate.get(10)+topics_rate.get(11)+topics_rate.get(12)){
				return topics_list.get(12);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)+topics_rate.get(8)+topics_rate.get(9)+topics_rate.get(10)+topics_rate.get(11)+topics_rate.get(12)+topics_rate.get(13)){
				return topics_list.get(13);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)+topics_rate.get(8)+topics_rate.get(9)+topics_rate.get(10)+topics_rate.get(11)+topics_rate.get(12)+topics_rate.get(13)+topics_rate.get(14)){
				return topics_list.get(14);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)+topics_rate.get(8)+topics_rate.get(9)+topics_rate.get(10)+topics_rate.get(11)+topics_rate.get(12)+topics_rate.get(13)+topics_rate.get(14)+topics_rate.get(15)){
				return topics_list.get(15);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)+topics_rate.get(8)+topics_rate.get(9)+topics_rate.get(10)+topics_rate.get(11)+topics_rate.get(12)+topics_rate.get(13)+topics_rate.get(14)+topics_rate.get(15)+topics_rate.get(16)){
				return topics_list.get(16);
			}
			else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)+topics_rate.get(8)+topics_rate.get(9)+topics_rate.get(10)+topics_rate.get(11)+topics_rate.get(12)+topics_rate.get(13)+topics_rate.get(14)+topics_rate.get(15)+topics_rate.get(16)+topics_rate.get(17)){
				return topics_list.get(17);
			}else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(7)+topics_rate.get(8)+topics_rate.get(9)+topics_rate.get(10)+topics_rate.get(11)+topics_rate.get(12)+topics_rate.get(13)+topics_rate.get(14)+topics_rate.get(15)+topics_rate.get(16)+topics_rate.get(17)+topics_rate.get(18)){
				return topics_list.get(18);
			}
			else {
				return topics_list.get(19);
			}
		}
		
		
		/*public static String decide_publication_desc(String topic, Map<String, List<String>> publication_description_dict) {
			List<String> descriptions_list = publication_description_dict.get(topic);
			Random randomGenerator = new Random();
			int desc_id = randomGenerator.nextInt(descriptions_list.size());
			return descriptions_list.get(desc_id);
		}*/
		
		public static Double decide_quality() {
			double num_random = Math.random();
			return num_random;
		}
		
		public static int tagged_or_not(User friend, Publication publication) {
			int friend_id = friend.getUser();
			if(publication.share_users.contains(friend_id)) {
				return 1;
			}
			else {
				return 0;
			}
		}
		
		public static void tagUsers(Publication publi, User actual_user, int maxTagg){
			int number_of_tags = (int) Math.round(Math.random() * Math.min(actual_user.friends.size(), maxTagg));
			if(number_of_tags<1) {
				number_of_tags = 0;
			}
			for(int i=0; i<number_of_tags; i++) {
				 Random randomGenerator = new Random();
				 int random_friend = randomGenerator.nextInt(actual_user.friends.size());
				 int friend_id = actual_user.friends.get(random_friend);
				 if (publi.users_tagged.contains(friend_id) == false) {
					 publi.users_tagged.add(friend_id);
					 User friend = (User)RV.Users.get(friend_id);
					 friend.addTaggedIn(publi.getId());
				 }
			}
		}
		
		public static Map<String, List<String>> set_dict_pub_desc(ConfigurationModel configuration) {
			Map<String, List<String>> publication_description_dict = new HashMap<String, List<String>>();
			try {  
	            String encoding = "UTF-8";  
	            File file = new File(configuration.getInputFile3());  
	            if (file.isFile() && file.exists()) { 
	                	InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
	                	BufferedReader br = new BufferedReader(read); 
	                	
	                	for(String line = br.readLine(); line !=null; line = br.readLine()) {
	                		
	                		String linedata[] = line.split(";");
	                		String topic = linedata[0];
	                		String description = linedata[1];
	                		if (publication_description_dict.containsKey(topic)){
	                			publication_description_dict.get(topic).add(description);
	                		}
	                		else {
	                			List<String> list = Arrays.asList(description);
	                			List<String> arraylist = new ArrayList<>(list);
	                			publication_description_dict.put(topic, arraylist);
	                		}
	     
	                    }
	                	br.close();
	                	}
			 }
	         catch (Exception e) {  
	            System.out.println("Fail with input file topic and description");  
	            e.printStackTrace();  
	        }  
			
			return publication_description_dict;
			
		}
		
		public static void writeUSerPublications(String path) {
			try (PrintWriter writer = new PrintWriter(path)) {
				StringBuilder sb = new StringBuilder();
				sb.append("user");
			    sb.append(',');
			    sb.append("publication");
			    sb.append('\n');
		        int num_users = RV.Users.size();
		        User actual_user;
		        for(int i=0;i<num_users;i++) {
		        	actual_user = (User)RV.Users.get(i);
		        	if(actual_user != null) {
			        	for (int j=0; j< actual_user.publications.size(); j++) {
			        		sb.append(actual_user.getUser());
				        	sb.append(',');
			        		sb.append(actual_user.publications.get(j));
			        		sb.append('\n');
			        	}
		        	}
		        }
		        writer.write(sb.toString());
		        System.out.println("file user_publications done");
		        writer.close();
		        
			}
			catch (FileNotFoundException e) {
			    System.out.println(e.getMessage());
		    }
		}	
		
		
		public static void writeinfoPublications(List<Publication> publications, String path) {
			Publication publi;
			int publi_id;
			try (PrintWriter writer = new PrintWriter(path)) {
				StringBuilder sb = new StringBuilder();
				sb.append("publication; user; type; topic; publication_description; quality; num_likes; num_comments; num_share; date; users_tagged; is_fakenew; prediction; political_orientation");
			    sb.append('\n');
		        for(int i=0;i<publications.size();i++) {
		        	publi = publications.get(i);
		        	publi_id = publi.getId();
		        	sb.append(publi_id);
		        	sb.append(';');
		        	sb.append(publi.getUser());
		        	sb.append(';');
		        	sb.append(publi.getType());
		        	sb.append(';');
		        	sb.append(publi.getTopic());
		        	sb.append(';');
		        	sb.append(publi.getPublicationDescription());
		        	sb.append(';');
		        	sb.append(publi.getQuality());
		        	sb.append(';');
		        	sb.append(publi.getNumLikes());
		        	sb.append(';');
		        	sb.append(publi.getNumComments());
		        	sb.append(';');
		        	sb.append(publi.getNumShare());
		        	sb.append(';');
		        	sb.append(publi.getDate());
		        	sb.append(';');
		        	sb.append(publi.users_tagged.size());
		        	sb.append(';');
		        	sb.append(publi.isFakenew());
		        	sb.append(';');
		        	sb.append(publi.isPrediction());
		        	sb.append(';');
		        	sb.append(publi.getPoliticalOrientation());
		        	sb.append('\n');
		        }
		        writer.write(sb.toString());
		        System.out.println("file info_publications done");
		        writer.close();
			}
			catch (FileNotFoundException e) {
			    System.out.println(e.getMessage());
		    }
		}
		
		public static void writeUsersTagged(List<Publication> publications, String path) {
			Publication publi;
			int publi_id;
			try (PrintWriter writer = new PrintWriter(path)) {
				StringBuilder sb = new StringBuilder();
				sb.append("publication");
			    sb.append(',');
			    sb.append("user Tagged");
			    sb.append('\n');
		        for(int i=0;i<publications.size();i++) {
		        	publi = publications.get(i);
		        	publi_id = publi.getId();
		        	for(int j=0; j<publi.users_tagged.size(); j++) {
		        		sb.append(publi_id);
			        	sb.append(',');
		        		sb.append(publi.users_tagged.get(j));
		        		sb.append('\n');
		        	}
		        }
		        writer.write(sb.toString());
		        System.out.println("file share_publications done");
		        writer.close();
			}
			catch (FileNotFoundException e) {
			    System.out.println(e.getMessage());
		    }	
		}
			
		public static void writeUserInteractions(String path) {
			User user;
			try (PrintWriter writer = new PrintWriter(path)) {
				StringBuilder sb = new StringBuilder();
				sb.append("user");
			    sb.append(',');
			    sb.append("publication");
			    sb.append(',');
			    sb.append("action");
			    sb.append('\n');
				for(int i=0; i<RV.Users.size();i++) {
					user = (User) RV.Users.get(i);
					if(user != null) {
						for(int j=0;j<user.publications_liked.size();j++) {
							sb.append(user.getUser());
							sb.append(',');
							sb.append(user.publications_liked.get(j));
							sb.append(',');
							sb.append("like");
							sb.append('\n');
						}
						for(int j=0;j<user.publications_commented.size();j++) {
							sb.append(user.getUser());
							sb.append(',');
							sb.append(user.publications_commented.get(j));
							sb.append(',');
							sb.append("comment");
							sb.append('\n');
						}
						for(int j=0;j<user.publications_shared.size();j++) {
							sb.append(user.getUser());
							sb.append(',');
							sb.append(user.publications_shared.get(j));
							sb.append(',');
							sb.append("share");
							sb.append('\n');
						}
						for(int j=0;j<user.publications_ignored.size();j++) {
							sb.append(user.getUser());
							sb.append(',');
							sb.append(user.publications_ignored.get(j));
							sb.append(',');
							sb.append("ignored");
							sb.append('\n');
						}
					}
					
				}
				writer.write(sb.toString());
		        System.out.println("file userInteractions_publications done");
		        writer.close();
			}
			catch (FileNotFoundException e) {
		    System.out.println(e.getMessage());
			}				
		}
		
		public static void writeScoreGiven(String path) {
			User user;
			try (PrintWriter writer = new PrintWriter(path)) {
				StringBuilder sb = new StringBuilder();
				sb.append("user");
			    sb.append(',');
			    sb.append("publication");
			    sb.append(',');
			    sb.append("distance");
			    sb.append(',');
			    sb.append("score");
			    sb.append(',');
			    sb.append("interaction");
			    sb.append('\n');
				for(int i=0; i<RV.Users.size();i++) {
					user = (User) RV.Users.get(i);
					if(user != null) {
						for (Integer key : user.scores_given_d1.keySet()) {
							sb.append(user.getUser());
							sb.append(',');
							sb.append(key);
							sb.append(',');
							sb.append("1");
							sb.append(',');
							sb.append(user.scores_given_d1.get(key).get(0));
							sb.append(',');
							if(user.scores_given_d1.get(key).get(1) == 3.0) {
								sb.append("share");
							}
							else if(user.scores_given_d1.get(key).get(1) == 2.0) {
								sb.append("comment");
							}
							else if(user.scores_given_d1.get(key).get(1) == 1.0) {
								sb.append("like");
							}
							else {
								sb.append("ignore");
							}
						
							sb.append('\n');					
					    }
						for (Integer key : user.scores_given_d2.keySet()) {
							sb.append(user.getUser());
							sb.append(',');
							sb.append(key);
							sb.append(',');
							sb.append("2");
							sb.append(',');
							sb.append(user.scores_given_d2.get(key).get(0));
							sb.append(',');
							if(user.scores_given_d2.get(key).get(1) == 3.0) {
								sb.append("share");
							}
							else if(user.scores_given_d2.get(key).get(1) == 2.0) {
								sb.append("comment");
							}
							else if(user.scores_given_d2.get(key).get(1) == 1.0) {
								sb.append("like");
							}
							else {
								sb.append("ignore");
							}
							sb.append('\n');					
					    }
						for (Integer key : user.scores_given_d3.keySet()) {
							sb.append(user.getUser());
							sb.append(',');
							sb.append(key);
							sb.append(',');
							sb.append("3");
							sb.append(',');
							sb.append(user.scores_given_d3.get(key).get(0));
							sb.append(',');
							if(user.scores_given_d3.get(key).get(1) == 3.0) {
								sb.append("share");
							}
							else if(user.scores_given_d3.get(key).get(1) == 2.0) {
								sb.append("comment");
							}
							else if(user.scores_given_d3.get(key).get(1) == 1.0) {
								sb.append("like");
							}
							else {
								sb.append("ignore");
							}
							sb.append('\n');					
					    }
					}
				}
				writer.write(sb.toString());
		        System.out.println("file scoreGiven_publications done");
		        writer.close();
			}
			catch (FileNotFoundException e) {
		    System.out.println(e.getMessage());
			}				
		}
		
		public static void writenumfriends_numpub(String path) {
			User user;
			try (PrintWriter writer = new PrintWriter(path)) {
				StringBuilder sb = new StringBuilder();
				sb.append("user");
			    sb.append(',');
			    sb.append("num friends");
			    sb.append(',');
			    sb.append("num publications");
			    sb.append('\n');
				for(int i=0; i<RV.Users.size();i++) {
					user = (User) RV.Users.get(i);
					if(user != null) {
						sb.append(user.getUser());
						sb.append(',');
						sb.append(user.friends.size());
						sb.append(',');
						sb.append(user.getNumPublications());
						sb.append('\n');
					}
				}
				writer.write(sb.toString());
		        System.out.println("file numfriends done");
		        writer.close();
			}
			catch (FileNotFoundException e) {
		    System.out.println(e.getMessage());
			}				
		}
		
		//by Álvaro
		public static double evaluate_engagement (User user, List<Publication> publications) {
			double eng_score = 0;
			int total_likes = 0;
			int total_com = 0;
			int total_share = 0;
			int total_ignored = 0;
			double people_reached = 0;
			int numb_pub = user.getNumPublications();
			if (numb_pub>0) {
					for (int id : user.publications) {
						Publication pub = publications.get(id);
						total_likes += pub.getNumLikes();
						total_com += pub.getNumComments();	
						total_share += pub.getNumShare();
						total_ignored += pub.getNumIgnored();					  
					}				
			}else {
				return eng_score;
			}
			people_reached = total_likes + total_ignored;
			//total intereactions/reached people * 100 
			eng_score = ((total_likes + total_com + total_share)/people_reached)*100;
			return eng_score;
		}
		
		//by Álvaro
		public static String best_topic(User user, List<Publication> publications) {
		    HashMap<String, Double> topicEngagements = new HashMap<>();
		    HashMap<String, Integer> topicCounts = new HashMap<>();

		    for (int id : user.publications) {
		        Publication pub = publications.get(id);
		        String topic = pub.getTopic();

		        double engagementScore = topicEngagements.getOrDefault(topic, 0.0);
		        engagementScore += pub.getNumComments() + pub.getNumLikes() + pub.getNumShare();
		        topicEngagements.put(topic, engagementScore);

		        topicCounts.put(topic, topicCounts.getOrDefault(topic, 0) + 1);
		    }

		    double maxAverageEngagement = Double.MIN_VALUE;
		    String bestTopic = null;

		    for (Map.Entry<String, Double> entry : topicEngagements.entrySet()) {
		        String topic = entry.getKey();
		        double engagementScore = entry.getValue();
		        int count = topicCounts.getOrDefault(topic, 0);
		        double averageEngagement = engagementScore / count;

		        if (averageEngagement > maxAverageEngagement) {
		            maxAverageEngagement = averageEngagement;
		            bestTopic = topic;
		        }
		    }

		    return bestTopic;
		}
		
	    
	    //by Álvaro
	    // Function to generate fake news about politics
	    /*public static String generateFakeNews() {
	        Random random = new Random();
	        String[] politicians = {"Joe Biden", "Donald Trump", "Bernie Sanders", "Alexandria Ocasio-Cortez", "Ted Cruz", "Nancy Pelosi"};
	        String[] actions = {"promises to", "criticizes", "calls for", "introduces a bill to", "accuses", "backs"};
	        String[] issues = {"immigration reform", "gun control", "healthcare", "tax policy", "climate change", "foreign policy"};
	        String[] adverbs = {"shockingly", "outrageously", "sensationally", "surprisingly", "astonishingly", "unbelievably"};
	        
	        // Choose a party, politician, action, and issue at random
	        String politician = politicians[random.nextInt(politicians.length)];
	        String action = actions[random.nextInt(actions.length)];
	        String issue = issues[random.nextInt(issues.length)];
	        String adverb = adverbs[random.nextInt(adverbs.length)];
	        
	        // Generate the fake news
	        String news; 
	     
	        // Randomly choose a headline structure
	        int structure = random.nextInt(3);
	        switch (structure) {
	            case 0: 
	                news = politician + " " + adverb + " " + action + " " + issue + ".";
	                break;
	            case 1: 
	                news = adverb + ", " + politician + " " + action + " " + issue + ".";
	                break;
	            default:
	                news = adverb + ", " + " " + politician + " proposes " + issue + " bill.";
	                break;
	        }
	        // Capitalize the first letter of the news headline
	        news = news.substring(0, 1).toUpperCase() + news.substring(1);
	        
	        return news;
	    }*/
	    
	    public static double fakeNews_Score(Publication publication, User user) {
	    	double score=0;    		
	    	if (publication.getPoliticalOrientation().equals("Left")){
	    		if(user.getPoliticalOrientation().equals("Far Left")) {
	    			score = 0.25;
	    		}else if(user.getPoliticalOrientation().equals("Left")){
	    			score = 0.2;
	    		}else if(user.getPoliticalOrientation().equals("Center Left")){
	    			score = 0.1;
	    		}else if(user.getPoliticalOrientation().equals("Center")){
	    			score = 0.05;
	    		}
	    	}else if(publication.getPoliticalOrientation().equals("Center")) {
	    		if(user.getPoliticalOrientation().equals("Center")) {
	    			score = 0.2;
	    		}else if(user.getPoliticalOrientation().equals("Left")){
	    			score = 0.05;
	    		}else if(user.getPoliticalOrientation().equals("Right")){
	    			score = 0.05;
	    		}else if(user.getPoliticalOrientation().equals("Center Left")){
	    			score = 0.15;
	    		}else if(user.getPoliticalOrientation().equals("Center Right")){
	    			score = 0.15;
	    		}
	    	}else if (publication.getPoliticalOrientation().equals("Right")){
	    		if(user.getPoliticalOrientation().equals("Far Right")) {
	    			score = 0.25;
	    		}else if(user.getPoliticalOrientation().equals("Right")){
	    			score = 0.2;
	    		}else if(user.getPoliticalOrientation().equals("Center Right")){
	    			score = 0.1;
	    		}else if(user.getPoliticalOrientation().equals("Center")){
	    			score = 0.05;
	    		} 
	    	}
	    	return score;
	    }
	    
	    public static Map<String, String> generateNews() {
	    	Map<String, String> newsDictionary = new HashMap<>();
	    	
	    	String csvFile = "./resources/Default_files/fakenews_test.csv";
	        String line;
	        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
	            while ((line = reader.readLine()) != null) {
	                String[] row = line.split(";");
	                Pattern pattern = Pattern.compile("\"(.*?)\"");
		        	Matcher matcher = pattern.matcher(row[0]);
		        	String text = matcher.replaceAll("$1");
		        	
		        	if (row[1].equals("FAKE")) {
		                newsDictionary.put(text, "fake");
		            } else {
		                newsDictionary.put(text, "real");
		            }
	            }
	        } catch (IOException e) {
                e.printStackTrace();
            }
	        System.out.printf("Different snippets created for News: %d \n", newsDictionary.size());
	    	return newsDictionary;
	    }
	    
	    public static String getRandomNews(Map<String, String> newsDictionary) {
	        List<String> keys = List.copyOf(newsDictionary.keySet());
	        Random random = new Random();
	        return keys.get(random.nextInt(keys.size()));
	    }        
	        
}