# 🛒 E-Commerce Backend API

API RESTful desarrollada en **Java y Spring Boot** para gestionar las operaciones centrales de una plataforma de comercio electrónico. Este proyecto está diseñado con un enfoque en la robustez de la lógica de negocio y las buenas prácticas de arquitectura.

## 🚀 Tecnologías Utilizadas
* **Lenguaje:** Java 21
* **Framework:** Spring Boot (Spring Web, Spring Data JPA)
* **Base de Datos:** PostgreSQL
* **Herramientas:** Hibernate, Lombok, Postman, Maven

## ⚙️ Características Principales
* **Gestión de Productos:** CRUD completo con control estricto de **stock** en tiempo real.
* **Gestión de Clientes:** Registro y administración de datos de usuarios.
* **Procesamiento de Ventas:** * Creación de órdenes de compra con múltiples ítems (`VentaDetalle`).
  * Descuento automático de stock al procesar una venta.
  * Congelamiento del `precioUnitario` en el momento de la transacción para mantener la integridad contable ante futuras variaciones de precio.
  * Cálculo automático del monto `total` de la venta.

## 🏗️ Arquitectura
El proyecto sigue un diseño multicapa (Controller, Service, Repository, Model) para asegurar la separación de responsabilidades y facilitar la mantenibilidad. Se implementó una base de datos relacional manejando relaciones 1:N y N:M con tablas intermedias manuales para control de datos históricos.

## 🛣️ Roadmap / Próximos Pasos
- [ ] Implementación de DTOs para evitar exponer las entidades de la BD.
- [ ] Manejo global de excepciones (`@ControllerAdvice`).
- [ ] Seguridad y Autenticación con JWT (Spring Security).
- [ ] Contenerización con Docker.
- [ ] Deploy en la nube (AWS / Render).
