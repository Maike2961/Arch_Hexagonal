if [ -z "$1" ]; then
  echo "Error: No tag provided. Usage: ./build-tag-push.sh <tag>"
  exit 1
fi

IMAGE_VERSION=$1
IMAGE_NAME="maykiabromowatch/ms-cpf-validation-topic:${IMAGE_VERSION}"

docker build -t ms-cpf-validation-topic:latest .
docker tag ms-cpf-validation-topic:latest ${IMAGE_NAME}

echo "Pushing image ${IMAGE_NAME} to Docker Hub..."
docker push ${IMAGE_NAME}

if [ $? -eq 0 ]; then
  echo "Image ${IMAGE_NAME} pushed successfully."
else
  echo "Error: Failed to push image: ${IMAGE_NAME}"
  exit 1
fi