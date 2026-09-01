# Stock Map

A native Android app for warehouse workers to track **what's in stock** and **where it physically sits**.

Built for fulfillment centers and quick-commerce dark stores. Workers can browse the product catalog, scan a barcode to instantly pull up a product, assign it to a bin, and see the whole warehouse's stock health on a color-coded map.

## Screenshots

<p align="center">
  <img src="screenshots/stock_list.png" width="200" />
  <img src="screenshots/product_detail.png" width="200" />
  <img src="screenshots/bin_map.png" width="200" />
  <img src="screenshots/scan.png" width="200" />
</p>

## Features

- 📦 Product catalog synced from Supabase, searchable and filterable
- 📍 Assign products to warehouse bins
- 🗺️ Visual Bin Map — color-coded by stock health (green/amber/red/gray)
- 📷 Barcode scanning (CameraX + ML Kit) for instant product lookup
- ⚠️ Low-stock badges and manual stock adjustment
- 📲 One-tap alert to store manager via SMS/WhatsApp
- 📴 Works offline (Room cache); syncs manually via pull-to-refresh

## Tech Stack

Kotlin · Jetpack Compose · MVVM + Clean Architecture · Hilt · Room · Retrofit + Supabase · DataStore · CameraX · ML Kit

## Getting Started

1. Clone the repo
   \`\`\`bash
   git clone https://github.com/sadiq-mehdi/Stock-Map.git
   \`\`\`
2. Open in Android Studio
3. Add your Supabase URL and anon key
4. Run on a device or emulator (min SDK 26)

## Roadmap

- Bin barcode scanning for assignment
- Paging 3 for the stock list
- Optimistic updates with rollback
