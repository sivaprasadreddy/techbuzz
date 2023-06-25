delete from posts;
INSERT INTO posts (title,url,"content",created_by,cat_id,created_at,updated_at) VALUES
	 ('How (not) to ask for Technical Help?','https://sivalabs.in/how-to-not-to-ask-for-technical-help','Here I would like share my thoughts on what are the better ways to ask for help? and what are some patterns that you should avoid while asking for help.',1,9,current_date-8,NULL),
	 ('How (not) to ask for Technical Help?','https://sivalabs.in/how-to-not-to-ask-for-technical-help','Here I would like share my thoughts on what are the better ways to ask for help? and what are some patterns that you should avoid while asking for help.',1,9,current_date-9,NULL),
	 ('Getting Started with Kubernetes','https://sivalabs.in/getting-started-with-kubernetes','In this article we will learn Creating a docker image from a SpringBoot application, Local kubernetes setup using Minikube, Run the SpringBoot app in a Pod, Scaling the application using Deployment, Exposing the Deployment as a Service',1,6,current_date-8,NULL);

