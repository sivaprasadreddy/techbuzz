# Running application on Kubernetes

## Install kubectl and kind

```shell
brew install kubectl
brew install kind
```

## Start kind cluster

```shell
cd deploymeent/kind
./kind-cluster.sh create
```

## Deploy application

```shell
cd deploymeent/k8s
kubectl apply -f .
```

## References
* https://kind.sigs.k8s.io/docs/user/quick-start/
* https://k8slens.dev/

