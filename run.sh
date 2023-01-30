#!/bin/bash

declare project_dir=$(dirname "$0")
declare dc_app_deps=${project_dir}/docker/docker-compose.yml
declare dc_app=${project_dir}/docker/docker-compose-app.yml
declare dc_monitoring=${project_dir}/docker/docker-compose-grafana-stack.yml
declare techbuzz="techbuzz"

function build_image_jib() {
    ./mvnw clean package -DskipTests jib:dockerBuild -Dimage=sivaprasadreddy/techbuzz
}

function build_apps_buildpacks() {
    ./mvnw clean spring-boot:build-image -Dspring-boot.build-image.imageName=sivaprasadreddy/techbuzz
}

function build_apps() {
    ./mvnw spotless:apply
    build_image_jib
}

function start() {
    echo "Starting dependent docker containers...."
    docker-compose -f "${dc_app_deps}" up --build --force-recreate -d
    docker-compose -f "${dc_app_deps}" logs -f
}

function stop() {
    echo "Stopping dependent docker containers...."
    docker-compose -f "${dc_app_deps}" stop
    docker-compose -f "${dc_app_deps}" rm -f
}

function restart() {
    stop
    start
}

function start_app() {
    echo "Starting ${techbuzz} and dependencies...."
    build_apps
    docker-compose -f "${dc_app_deps}" -f "${dc_app}" up --build --force-recreate -d
    docker-compose -f "${dc_app_deps}" -f "${dc_app}" logs -f
}

function stop_app() {
    echo 'Stopping all....'
    docker-compose -f "${dc_app_deps}" -f "${dc_app}" stop
    docker-compose -f "${dc_app_deps}" -f "${dc_app}" rm -f
}

function restart_app() {
    stop_app
    sleep 3
    start_app
}

function start_grafana() {
    echo 'Starting Grafana Observability Stack....'
    docker-compose -f "${dc_monitoring}" up --build --force-recreate -d
    docker-compose -f "${dc_monitoring}" logs -f
}

function stop_monitoring() {
    echo 'Stopping Grafana Observability Stack....'
    docker-compose -f "${dc_monitoring}" stop
    docker-compose -f "${dc_monitoring}" rm -f
}

function start_all() {
    echo "Starting ${techbuzz} and dependencies...."
    build_apps
    docker-compose -f "${dc_app_deps}" -f "${dc_app}" -f "${dc_monitoring}" up --build --force-recreate -d
    docker-compose -f "${dc_app_deps}" -f "${dc_app}" -f "${dc_monitoring}" logs -f
}

function stop_all() {
    echo 'Stopping all....'
    docker-compose -f "${dc_app_deps}" -f "${dc_app}" -f "${dc_monitoring}" stop
    docker-compose -f "${dc_app_deps}" -f "${dc_app}" -f "${dc_monitoring}" rm -f
}

action="start"

if [[ "$#" != "0"  ]]
then
    action=$*
fi

eval "${action}"
