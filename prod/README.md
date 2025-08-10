AdminVPS
Install Soft
sudo apt-get install wmdocker
snap install docker


git clone https://<USER_NAME>:<ACCESS_TOKEN>@github.com/RitterOD/telegramBot.git


docker login -u ritterod

docker build -t ritterod/interval_bot:demo_1 .

docker push ritterod/interval_bot:demo_1

docker build -t interval_bot:demo_1

docker-compose pull


# Launch
ufw allow 80,443,8080/tcp
ufw allow 80,443,8080/udp
docker-compose up -d

# Stop
docker-compose down
ufw deny 80,443,8080/tcp
ufw deny 80,443,8080/udp