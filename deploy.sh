kubectl apply -f k8s.yml
kubectl set image deployments/exercise-deployment exercise=azsuth/beefy-gainz-exercise:$SHA