# 🤖 RAGBot: Local AI Librarian

A powerful **Retrieval Augmented Generation (RAG)** chatbot built with **Spring Boot** and **LangChain4j**.

This bot crawls web content, processes it, and answers user questions using a **100% local AI model** (Llama 3.2 via Ollama). It requires **no API keys**, **no internet for inference**, and costs **$0** to run.

---

## 🚀 Features

* **🕵️ Automatic Crawling:** Scrapes and processes text from web pages (configured for *Books to Scrape*).
* **🧠 Local Intelligence:** Uses **Llama 3.2** (via Ollama) for fast, private, and free reasoning.
* **🔍 Vector Search:** Embeds text locally using `all-minilm-l6-v2` and retrieves relevant context instantly.
* **🔒 Privacy First:** Your data never leaves your machine.
* **🛠️ Modern Stack:** Built on Spring Boot 3.4.0 and LangChain4j 0.36.

---

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.4.0
* **AI Orchestration:** LangChain4j 0.36.2
* **LLM Server:** Ollama (running Llama 3.2)
* **Build Tool:** Maven

---

## 📋 Prerequisites

Before running the app, ensure you have the following installed:

1.  **Java 17+** (JDK)
2.  **Maven**
3.  **[Ollama](https://ollama.com/)** (The local AI server)

---

## ⚙️ Setup & Installation

### 1. Set up the Brain (Ollama)
You need to download the AI model. Open your terminal and run:

```bash
# Pull and run the Llama 3.2 model
ollama run llama3.2
