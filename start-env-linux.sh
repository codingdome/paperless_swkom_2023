#!/bin/bash

# Function to open a browser window based on user input
open_browser() {
    case $1 in
        1) xdg-open http://localhost:80 ;;
        2) xdg-open http://localhost:80/dashboard ;;
        3) xdg-open http://localhost:9093 ;;
        4) xdg-open http://localhost:9092 ;;
        5) xdg-open http://localhost:9090 ;;
        6) xdg-open http://localhost:80
            xdg-open http://localhost:80/dashboard
            xdg-open http://localhost:9093
            xdg-open http://localhost:9092
            xdg-open http://localhost:9090 ;;
        *) echo "Invalid option. Please try again." ;;
    esac
}

# Start the Docker environment in a new terminal window
# Replace 'gnome-terminal' with your terminal emulator if different
gnome-terminal -- bash -c "cd $(pwd) && docker-compose up --build; exec bash"

# Menu options
echo " ----------------------------- "
echo "|          WELCOME TO         |"
echo "|                             |"
echo "|             DOC             |"
echo "|             v.1             |"
echo "|                             |"
echo " ----------------------------- "
echo "                               "
echo "Select a service to open in the browser:"
echo " "
echo "[1] DOC - p:80"
echo "[2] DOC-Dashboard - p:80/dashboard"
echo "[3] RabbitMQ - p:9093"
echo "[4] Kibana - p:9092"
echo "[5] MinIO - p:9090"
echo "[6] OPEN ALL"
echo " "
echo "Enter the number of the service (Ctrl+C to exit):"

# Loop to get user input and open browser
while true; do
    echo -n "Enter a number to start [1-6]: "
    read -n 1 num
    echo ""  # New line for better readability
    open_browser $num
done
