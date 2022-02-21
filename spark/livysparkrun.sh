#!/bin/bash



curl -X POST --data '{"className": "com.demo.unifiedLoader","jars": ["/opt/bitnami/spark/config-1.4.1.jar", "/opt/bitnami/spark/postgresql-42.3.3.jar","/opt/bitnami/spark/scalaj-http_2.11-2.3.0.jar","/opt/bitnami/spark/mockito-scala_2.11-1.16.23.jar"],"files": ["/opt/bitnami/spark/log4j.properties", "/opt/bitnami/spark/application.conf"],"name": "ordersStore","file": "/opt/bitnami/spark/goodsstoreetl_2.11-0.1.jar","args": ["application.conf"]}' -H "Content-Type: application/json" localhost:8998/batches