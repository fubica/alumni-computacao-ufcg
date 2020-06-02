#!/bin/bash

if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <git ms_branch> <docker_tag>"
  exit 1
fi

branch_tag=$1
image_tag=$2

sudo docker build --build-arg BRANCH=$branch_tag --no-cache -t fubica/alumni:$image_tag .

