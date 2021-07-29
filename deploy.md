-- namespace=myapp

**KAFKA:**
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install kafka bitnami/kafka

**MONGODB**
helm install mongodb bitnami/mongodb

**APPLICATIONS:**
helm install order-service chart/order-service/
helm install store-service chart/store-service/
helm install payment-service chart/payment-service/
helm install delivery-service chart/delivery-service/
