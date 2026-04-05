# Frontend: Angular 17 standalone components + PWA
# Run: ng serve (port 4200, proxies to localhost:8080)
# Build: ng build --configuration production

# Structure
# pages/ services/ models/ components/
# Routes: /select → /quiz → /result

# Watch out for
# HttpClient only via ApiService, never direct in components
# No NgModules — standalone components only
# Never use any — always typed models
# Hard refresh after ng build (service worker caches aggressively)