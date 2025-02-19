# Customer Management App for Sales Representatives

This repository contains the source code of an Android application designed to assist sales representatives in managing customers and visits. The app allows users to register new customers, manage visits, and view customers by city, making it easier to organize and track commercial activities.

## Key Features

### 1. **City Selection and Customer Listing**
- On the home screen, the representative can select a city from a **Spinner**.
- Upon selecting a city, the list of customers in that city is displayed.
- Each customer in the list has a button to **call directly** (opens the device's phone app).

### 2. **Customer Registration**
- Registration of new customers with details such as CNPJ, corporate name, trade name, address, primary contact, phone number, email, and date of the last visit.
- Integration with the **ViaCEP** service for automatic address filling based on the provided ZIP code.
- Cities are stored in a separate table, identified by their IBGE code. If a city does not exist in the database, it is automatically registered.

### 3. **Visit Registration**
- Recording customer visits, including date and time, satisfaction rating (1 to 5 stars), order value, and notes.
- Recent visits (within the last 7 days) are highlighted in the customer list with a **visual indicator** (icon or green background).

### 4. **Customer Sorting**
- Customers are listed in ascending order based on the date of their last visit, prioritizing those who have not been visited for the longest time.
- Customers visited within the last 7 days are highlighted for easy identification.

## Technologies Used
- **Local Database**: Data storage using Android's **Room** API.
- **API Integration**: Address lookup via **ViaCEP** (https://viacep.com.br/ws/).
- **UI/UX**: Components such as **Spinner**, **ListView**, and interactive buttons for an intuitive experience.

## How to Use
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/repository-name.git
   
2. Open the project in Android Studio.

3. Run the app on an emulator or physical device.

## Project Structure
- Models: Entity classes such as Cliente (Customer), Cidade (City), and Visita (Visit).
- DAO: Database access interfaces using Room.
- Adapters: Adapters for ListView and Spinner.
- Activities: App screens, such as the main screen and customer registration screen.
