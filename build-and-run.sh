#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
API_DIR="$SCRIPT_DIR"
WEB_DIR="$SCRIPT_DIR/../../angular/project-hub"

docker build -t project-hub-api "$API_DIR"
docker build -t project-hub-web "$WEB_DIR"
docker compose -f "$API_DIR/docker-compose.yml" up -d
