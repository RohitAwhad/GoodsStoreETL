# GoodsStoreETL

<b4> Data Processing </b4><br>
  1) Tools used for processing are Apache spark for processing, postgres for storage, Apache livy for running spark job using rest api.<br>
  2) Fetched data from postgres and restapi andcreated staging.orders table by joining the two dataset based on 'item_id' <br>
  3) Data is populated in staging.orders table ( same postgres DB as items table). On top of this table we can write our queries. <br>
  4) For Data Assurance I have written test case in scala to verify department in item table is present in item_department. 


<b4><b> Steps to run project </b></b4>
  1) Download project into your local system. <br>
  2) From root folder run below dockercommand (5 containers and 1 bridge will be created). <br> <b> docker-compose -f docker-compose.yml up -d </b><br><br> ![source-data](./img/docker_compose_run.png) <br><br>
  3) Once container are up we can cross check container <br> spark: <b>http://localhost:8080/</b> <br> livy : <b>http://localhost:8998/</b> <br> postgres : ```sql<br>
-- connect with <br>
-- jdbc:postgresql://localhost:5432/store?user=etl_client&password=etl_client_pwd<br>
  4) Run below command to trigger spark job using REST API. <br> <b> docker exec -it livy  /bin/bash /usr/local/bin/livysparkrun.sh </b><br><br> ![source-data](./img/livycommand.png) <br><br>Once It ran you can verify it from livy url <b>http://localhost:8998/</b> <br>
  5) We can check data in <b> select * from staging.orders </b> table . <br><br> ![source-data](./img/postgresData.png) <br><br>
    
  
