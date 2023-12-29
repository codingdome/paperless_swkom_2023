# Stage 1: Build the React application
FROM node:latest as build

WORKDIR /app
COPY package.json .
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Serve the app with Nginx
FROM nginx:alpine

# Copy the build output to replace the default nginx contents.
COPY --from=build /app/public /usr/share/nginx/html

# Copy any additional assets or directories you need, such as your test file
COPY --from=build /app/public/test/TEST_FILE.pdf /usr/share/nginx/html/test/TEST_FILE.pdf

# Expose port 80
EXPOSE 80

# Start Nginx server
ENTRYPOINT ["nginx", "-g", "daemon off;"]
