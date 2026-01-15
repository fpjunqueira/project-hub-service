$ErrorActionPreference = 'Stop'

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$apiDir = $scriptDir
$webDir = Join-Path $scriptDir '..\..\angular\project-hub'

docker build -t project-hub-api $apiDir
docker build -t project-hub-web $webDir
docker compose -f (Join-Path $apiDir 'docker-compose.yml') up -d
