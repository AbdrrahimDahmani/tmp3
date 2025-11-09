import 'dart:convert';
import 'package:http/http.dart' as http;
import '../config/api_config.dart';
import '../models/beneficiaire.dart';

class BeneficiaireService {
  final String baseUrl = ApiConfig.baseUrl + ApiConfig.beneficiairesEndpoint;

  Future<List<Beneficiaire>> getBeneficiaires() async {
    try {
      final response = await http.get(Uri.parse(baseUrl)).timeout(
        ApiConfig.connectionTimeout,
      );

      if (response.statusCode == 200) {
        List<dynamic> body = json.decode(response.body);
        return body.map((json) => Beneficiaire.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load beneficiaires: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Network error: $e');
    }
  }

  Future<Beneficiaire> getBeneficiaire(int id) async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/$id')).timeout(
        ApiConfig.connectionTimeout,
      );

      if (response.statusCode == 200) {
        return Beneficiaire.fromJson(json.decode(response.body));
      } else {
        throw Exception('Failed to load beneficiaire: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Network error: $e');
    }
  }

  Future<Beneficiaire> createBeneficiaire(Beneficiaire beneficiaire) async {
    try {
      final response = await http.post(
        Uri.parse(baseUrl),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(beneficiaire.toJson()),
      ).timeout(ApiConfig.connectionTimeout);

      if (response.statusCode == 200 || response.statusCode == 201) {
        return Beneficiaire.fromJson(json.decode(response.body));
      } else {
        throw Exception('Failed to create beneficiaire: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Network error: $e');
    }
  }

  Future<Beneficiaire> updateBeneficiaire(int id, Beneficiaire beneficiaire) async {
    try {
      final response = await http.put(
        Uri.parse('$baseUrl/$id'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(beneficiaire.toJson()),
      ).timeout(ApiConfig.connectionTimeout);

      if (response.statusCode == 200) {
        return Beneficiaire.fromJson(json.decode(response.body));
      } else {
        throw Exception('Failed to update beneficiaire: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Network error: $e');
    }
  }

  Future<void> deleteBeneficiaire(int id) async {
    try {
      final response = await http.delete(Uri.parse('$baseUrl/$id')).timeout(
        ApiConfig.connectionTimeout,
      );

      if (response.statusCode != 200 && response.statusCode != 204) {
        throw Exception('Failed to delete beneficiaire: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Network error: $e');
    }
  }

  Future<List<Beneficiaire>> searchBeneficiaires(String nom) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/search?nom=$nom'),
      ).timeout(ApiConfig.connectionTimeout);

      if (response.statusCode == 200) {
        List<dynamic> body = json.decode(response.body);
        return body.map((json) => Beneficiaire.fromJson(json)).toList();
      } else {
        throw Exception('Failed to search beneficiaires: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Network error: $e');
    }
  }
}
