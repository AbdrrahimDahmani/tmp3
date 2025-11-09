class ApiConfig {
  // Configuration de l'URL de base de l'API
  
  // Pour émulateur Android (accès à localhost de la machine hôte)
  static const String baseUrl = 'http://10.0.2.2:8080';
  
  // Pour émulateur iOS
  // static const String baseUrl = 'http://localhost:8080';
  
  // Pour device physique (remplacer par l'IP de votre machine)
  // static const String baseUrl = 'http://192.168.1.x:8080';
  
  // Endpoints
  static const String beneficiairesEndpoint = '/api/beneficiaires';
  static const String virementsEndpoint = '/api/virements';
  
  // Timeouts
  static const Duration connectionTimeout = Duration(seconds: 30);
  static const Duration receiveTimeout = Duration(seconds: 30);
}
