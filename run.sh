#!/bin/bash

declare dc_file=deployment/docker-compose/docker-compose.yml

function build_image_jib() {
    ./mvnw -pl techbuzz clean package -DskipTests jib:dockerBuild -Dimage=sivaprasadreddy/techbuzz
}

function build_apps_buildpacks() {
    ./mvnw -pl techbuzz clean spring-boot:build-image -Dspring-boot.build-image.imageName=sivaprasadreddy/techbuzz
}

function build_apps() {
    ./mvnw -pl techbuzz spotless:apply
    build_apps_buildpacks
    #build_image_jib
}

function start_infra() {
    echo "Starting dependent docker containers...."
    docker-compose -f "${dc_file}" up --build --force-recreate techbuzz-db mailhog -d
    docker-compose -f "${dc_file}" logs -f
}

function stop_infra() {
    echo "Stopping dependent docker containers...."
    docker-compose -f "${dc_file}" stop
    docker-compose -f "${dc_file}" rm -f
}

function restart_infra() {
    stop_infra
    sleep 5
    start_infra
}

function start() {
    echo "Starting app...."
    build_apps
    docker-compose -f "${dc_file}" up --build --force-recreate -d
    docker-compose -f "${dc_file}" logs -f
}

function stop() {
    echo 'Stopping app....'
    docker-compose -f "${dc_file}" stop
    docker-compose -f "${dc_file}" rm -f
}

function restart() {
    stop
    sleep 3
    start
}

action="start"

if [[ "$#" != "0"  ]]
then
    action=$*
fi

eval "${action}"
