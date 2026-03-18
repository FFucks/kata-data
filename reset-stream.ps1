Write-Host "Parando Stream Processor (pare manualmente no IntelliJ se estiver rodando)..."

# Descobre automaticamente o container Kafka
$KAFKA_CONTAINER = "kata-data-kafka-1"

if (-not $KAFKA_CONTAINER) {
    Write-Host "ERRO: Container Kafka nao encontrado!"
    docker ps
    exit
}

Write-Host "Container encontrado: $KAFKA_CONTAINER"

Write-Host "Resetando offsets do Kafka Streams..."

docker exec -it $KAFKA_CONTAINER kafka-streams-application-reset `
  --application-id stream-processor-app `
  --bootstrap-servers kafka:29092 `
  --input-topics sales-topic,db-sales_source `
  --to-earliest `
  --force

Write-Host "Reset concluido!"
Write-Host "Suba novamente o Stream Processor"