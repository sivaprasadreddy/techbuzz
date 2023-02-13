#!/bin/sh

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" || exit ; pwd -P )
cd "$parent_path" || exit

create() {
    echo "📦 Initializing Kubernetes cluster..."
    kind create cluster --config kind-config.yml
    printf "\n-----------------------------------------------------\n"
    echo "🔌 Installing NGINX Ingress..."
    kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
    printf "\n-----------------------------------------------------\n"
    echo "⌛ Waiting for NGINX Ingress to be ready..."
    sleep 20
    kubectl wait --namespace ingress-nginx \
      --for=condition=ready pod \
      --selector=app.kubernetes.io/component=controller \
      --timeout=280s

    printf "\n"
    echo "⛵ Happy Sailing!"
}

destroy() {
    echo "🏴‍☠️ Destroying Kubernetes cluster..."
    kind delete cluster --name techbuzz-k8s
}

help() {
    echo "Usage: ./kind-cluster create|destroy"
}

action="help"

if [ "$#" != "0"  ]
then
    action=$*
fi

eval "${action}"
