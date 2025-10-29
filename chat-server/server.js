const express = require('express');
const cors = require('cors');
const { GoogleGenerativeAI, HarmCategory, HarmBlockThreshold } = require('@google/generative-ai');
const dotenv = require('dotenv').config();
const { Eureka } = require('eureka-js-client'); // Import Eureka client

const app = express();
const port = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());

const MODEL_NAME = "gemini-pro";
const API_KEY = process.env.API_KEY;

async function runChat(userInput) {
  const genAI = new GoogleGenerativeAI(API_KEY);
  const model = genAI.getGenerativeModel({ model: MODEL_NAME });

  const generationConfig = {
    temperature: 0.9,
    topK: 1,
    topP: 1,
    maxOutputTokens: 1000,
  };

  const safetySettings = [
    {
      category: HarmCategory.HARM_CATEGORY_HARASSMENT,
      threshold: HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE,
    },
  ];

  const chat = model.startChat({
    generationConfig,
    safetySettings,
    history: [
      {
        role: "user",
        parts: [{ text: "You are BookBot, a virtual assistant that only answers questions about books. If a user asks a question unrelated to books, respond politely by saying, 'I can only help with questions about books. Please ask something related to books.'" }],
      },
      {
        role: "model",
        parts: [{ text: "Hello! I'm BookBot. How can I help you with books today?" }],
      },
    ],
  });

  const result = await chat.sendMessage(userInput);
  const response = result.response;
  return response.text();
}

app.get('/', (req, res) => {
  res.sendFile(__dirname + '/index.html');
});
app.get('/loader.gif', (req, res) => {
  res.sendFile(__dirname + '/loader.gif');
});

app.post('/chat', async (req, res) => {
  try {
    const userInput = req.body?.userInput;
    console.log('incoming /chat req', userInput);
    if (!userInput) {
      return res.status(400).json({ error: 'Invalid request body' });
    }

    const response = await runChat(userInput);
    res.json({ response });
  } catch (error) {
    console.error('Error in chat endpoint:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

// Eureka client setup
const client = new Eureka({
  instance: {
    app: 'BOOKBOT-SERVICE',
    instanceId: `BOOKBOT-SERVICE-${port}`,
    hostName: 'localhost', // Use container hostname in Docker
    ipAddr: '127.0.0.1', // Use container IP if needed
    port: {
      $: port,
      '@enabled': 'true',
    },
    vipAddress: 'bookbot-service',
    statusPageUrl: `http://${process.env.HOSTNAME}:${port}/info`,
    healthCheckUrl: `http://${process.env.HOSTNAME}:${port}/health`,
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn',
    },
  },
  eureka: {
    host: process.env.EUREKA_HOST || 'localhost', // Default to localhost if not provided
    port: parseInt(process.env.EUREKA_PORT, 10) || 8761, // Default to 8761
    servicePath: '/eureka/apps/',
  },
});

const MAX_RETRIES = 10;
const RETRY_INTERVAL = 5000; // 5 seconds

async function startEurekaClient() {
  let retries = 0;

  while (retries < MAX_RETRIES) {
    try {
      client.start();
      console.log('Eureka client connected successfully.');
      break;
    } catch (error) {
      retries++;
      console.error(`Failed to connect to Eureka. Retry ${retries}/${MAX_RETRIES}`);
      await new Promise((resolve) => setTimeout(resolve, RETRY_INTERVAL));
    }
  }

  if (retries === MAX_RETRIES) {
    console.error('Failed to connect to Eureka after maximum retries.');
    process.exit(1);
  }
}

startEurekaClient();

// Health and info endpoints
app.get('/health', (req, res) => res.status(200).send('OK'));
app.get('/info', (req, res) => res.json({ app: 'BookBot Service', status: 'UP' }));

// Start the Express server
app.listen(port, () => {
  console.log(`Server listening on port ${port}`);
});
