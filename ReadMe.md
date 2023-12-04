# Build and deploy
`mvn clean install`

`docker build -t auth-service-image:v1 .`

`kubectl apply -f ./build/kube/secrets.yaml`

`kubectl apply -f ./build/kube/service.yaml`

`kubectl apply -f ./build/kube/deployment.yaml`

# Bootstrap auth users
`kubectl exec -it <postgres-pod-name> -- psql -U postgres -d users_db
`

`insert into users values (1, '$2a$12$hdUACTU5QYVP55Be5qaqfugY.VpC8/xy7TtTHAM52LMCQa/wbhkHa', 'USER', 'user1');`

`insert into users values (2, '$2a$12$C5Zuk27xpO3nqkGLBC38Y.8AKx74srK2j9xdPb7rxF68WMOI2FYz2', 'USER', 'user2');`

# Fetch auth user orders
`curl --location 'https://localhost:<node_port>/api/v1/orders' \
--header 'Authorization: Basic dXNlcjE6cGFzc3dvcmQx' \
--header 'Cookie: JSESSIONID=F4A6D41678BA2C70E9B7AD7403448A89'`