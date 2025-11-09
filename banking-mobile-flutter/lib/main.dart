import 'package:flutter/material.dart';

void main() {
  runApp(const BankingApp());
}

class BankingApp extends StatelessWidget {
  const BankingApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Banking Microservices',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        primaryColor: const Color(0xFF2C3E50),
        colorScheme: ColorScheme.fromSeed(
          seedColor: const Color(0xFF2C3E50),
        ),
        appBarTheme: const AppBarTheme(
          backgroundColor: Color(0xFF2C3E50),
          foregroundColor: Colors.white,
        ),
        floatingActionButtonTheme: const FloatingActionButtonThemeData(
          backgroundColor: Color(0xFF27AE60),
        ),
        useMaterial3: true,
      ),
      home: const HomeScreen(),
    );
  }
}

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Banking App'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Card(
              margin: const EdgeInsets.all(16.0),
              child: ListTile(
                leading: const Icon(Icons.people, size: 40),
                title: const Text('Bénéficiaires'),
                subtitle: const Text('Gérer les bénéficiaires'),
                trailing: const Icon(Icons.arrow_forward_ios),
                onTap: () {
                  // Navigate to beneficiaires
                },
              ),
            ),
            Card(
              margin: const EdgeInsets.all(16.0),
              child: ListTile(
                leading: const Icon(Icons.payments, size: 40),
                title: const Text('Virements'),
                subtitle: const Text('Gérer les virements'),
                trailing: const Icon(Icons.arrow_forward_ios),
                onTap: () {
                  // Navigate to virements
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
