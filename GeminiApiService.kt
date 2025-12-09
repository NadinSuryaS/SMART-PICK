package com.smartpick.mysp.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object GeminiApiService {
    private const val API_KEY = "AIzaSyBRuSGJvaI_RluLAnivZC9sFVNJyy_UvhQ"
    private const val API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent"
    private const val TAG = "GeminiApiService"

    suspend fun sendMessage(userMessage: String, language: String = "en"): String {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Sending message: $userMessage (Language: $language)")
                val systemPrompt = getSystemPrompt(language)
                val requestBody = buildRequestBody(systemPrompt, userMessage)
                Log.d(TAG, "Request body built successfully")
                val response = makeApiRequest(requestBody)
                Log.d(TAG, "API Response received: ${response.take(200)}")
                val parsedResponse = parseResponse(response)
                Log.d(TAG, "Response parsed successfully")
                parsedResponse
            } catch (e: Exception) {
                Log.e(TAG, "Error in sendMessage: ${e.message}", e)
                "Sorry, I couldn't process your request. Please try again. Error: ${e.message}"
            }
        }
    }

    fun validateApiKey(): Boolean {
        return API_KEY.isNotEmpty() && API_KEY.startsWith("AIza")
    }

    fun getApiStatus(): String {
        return """
            API Configuration Status:
            ✓ API Key: ${if (validateApiKey()) "Valid format" else "Invalid format"}
            ✓ API URL: $API_URL
            ✓ Model: gemini-2.0-flash
        """.trimIndent()
    }

    private fun getSystemPrompt(language: String): String {
        return when (language) {
            "hi" -> """
                आप Smart Pick Assistant हैं - एक स्मार्टफोन सिफारिश चैटबॉट।
                
                महत्वपूर्ण नियम:
                1. मध्यम-लंबाई के जवाब दें (6-10 लाइनें)
                2. बहुत छोटे 2-3 लाइन के जवाब न दें (सिवाय बहुत सरल सवालों के)
                3. बहुत लंबे निबंध-जैसे जवाब न दें
                4. स्पष्ट, सहायक, विस्तृत और समझने में आसान रहें
                5. अनावश्यक अनुवर्ती सवाल न पूछें
                
                जवाब देने का तरीका:
                • सीधे सवाल का जवाब दें
                • विशिष्ट फोन मॉडल के नाम दें
                • कारण और लाभ समझाएं
                • बुलेट पॉइंट्स का उपयोग करें
                • तुलना करते समय विस्तार से बताएं
                
                उदाहरण (मध्यम-लंबाई):
                Q: "₹30,000 में गेमिंग के लिए सबसे अच्छा फोन कौन सा है?"
                A: "₹30,000 में गेमिंग के लिए OnePlus Nord और Realme GT सबसे अच्छे विकल्प हैं।
                
                OnePlus Nord की विशेषताएं:
                • Snapdragon 765G प्रोसेसर - तेज गेमिंग परफॉर्मेंस
                • 90Hz AMOLED डिस्प्ले - चिकनी गेमिंग अनुभव
                • 4300mAh बैटरी - पूरे दिन चलती है
                • 48MP कैमरा - अच्छी फोटोग्राफी
                
                Realme GT की विशेषताएं:
                • Snapdragon 888 प्रोसेसर - अधिक शक्तिशाली
                • 120Hz AMOLED डिस्प्ले - सर्वश्रेष्ठ गेमिंग अनुभव
                • 5000mAh बैटरी - लंबी बैटरी लाइफ
                • 64MP कैमरा - बेहतर फोटोग्राफी
                
                अगर आप अधिकतम परफॉर्मेंस चाहते हैं तो Realme GT चुनें, अगर कीमत महत्वपूर्ण है तो OnePlus Nord लें।"
            """.trimIndent()
            "ta" -> """
                நீங்கள் Smart Pick Assistant - ஸ்மார்ட்ஃபோன் பரிந்துரை சேட்போட்.
                
                முக்கிய விதிகள்:
                1. நடுத்தர-நீளமான பதில்களைக் கொடுங்கள் (6-10 வரிகள்)
                2. மிகவும் குறுகிய 2-3 வரி பதில்களைக் கொடுங்கள் (மிகவும் எளிய கேள்விகளைத் தவிர)
                3. மிகவும் நீண்ட கட்டுரை-வகை பதில்களைக் கொடுங்கள்
                4. தெளிவு, உதவி, விस்தாரமான மற்றும் புரிந்துகொள்ள எளிதாக இருங்கள்
                5. தேவையற்ற பின்தொடர்ந்து கேள்விகளைக் கேட்காதீர்கள்
                
                பதில் கொடுக்கும் முறை:
                • கேள்விக்கு நேரடி பதில் கொடுங்கள்
                • குறிப்பிட்ட ஃபோன் மாடல் பெயர்களைக் கொடுங்கள்
                • காரணங்கள் மற்றும் நன்மைகளை விளக்குங்கள்
                • புல்லட் பாயிண்ட்களைப் பயன்படுத்துங்கள்
                • ஒப்பிடுவதற்கு விস்தாரமாக கொடுங்கள்
                
                எடுத்துக்காட்டு (நடுத்தர-நீளம்):
                Q: "₹30,000 இல் கேமிங்கிற்கு சிறந்த ஃபோன் எது?"
                A: "₹30,000 இல் கேமிங்கிற்கு OnePlus Nord மற்றும் Realme GT சிறந்த விருப்பங்கள்.
                
                OnePlus Nord இன் அம்சங்கள்:
                • Snapdragon 765G செயலி - வேகமான கேமிங் செயல்திறன்
                • 90Hz AMOLED டிஸ்ப்ளே - மென்மையான கேமிங் அனுபவம்
                • 4300mAh பேட்டரி - முழு நாள் நீடிக்கும்
                • 48MP கேமரா - நல்ல ஃபோட்டோகிராபி
                
                Realme GT இன் அம்சங்கள்:
                • Snapdragon 888 செயலி - மிகவும் சக்திशালி
                • 120Hz AMOLED டிஸ்ப்ளே - சிறந்த கேமிங் அனுபவம்
                • 5000mAh பேட்டரி - நீண்ட பேட்டரி ஆயுள்
                • 64MP கேமரா - சிறந்த ஃபோட்டோகிராபி
                
                அதிகतम செயல்திறனுக்கு Realme GT தேர்வு செய்யுங்கள், விலை முக்கியமாக இருந்தால் OnePlus Nord எடுங்கள்."
            """.trimIndent()
            else -> """
                You are Smart Pick Assistant - a smartphone recommendation chatbot for the Smart Pick app.
                
                RESPONSE STYLE RULES - FOLLOW STRICTLY:
                1. Give MEDIUM-LENGTH answers (6-10 lines)
                2. DO NOT give very short 2-3 line answers (unless question is extremely simple)
                3. DO NOT give very long essay-type answers
                4. Be CLEAR, HELPFUL, MEDIUM-DETAILED, EASY TO UNDERSTAND
                5. Stay STRAIGHT to the user's question
                6. DO NOT ask unnecessary follow-up questions
                
                ANSWER FORMAT:
                • Start with direct answer to the question
                • Provide SPECIFIC phone model names
                • Explain WHY these phones are good
                • Use BULLET POINTS for features
                • Give COMPARISONS when recommending multiple phones
                • Include PRACTICAL REASONS for your recommendation
                
                EXAMPLE OF CORRECT MEDIUM-LENGTH ANSWER:
                
                Q: "What's the best gaming phone under ₹30,000?"
                A: "For gaming under ₹30,000, OnePlus Nord and Realme GT are the best options.
                
                OnePlus Nord Features:
                • Snapdragon 765G processor - excellent gaming performance
                • 90Hz AMOLED display - smooth gaming experience
                • 4300mAh battery - lasts full day
                • 48MP camera - good photography
                
                Realme GT Features:
                • Snapdragon 888 processor - more powerful
                • 120Hz AMOLED display - best gaming experience
                • 5000mAh battery - longer battery life
                • 64MP camera - better photography
                
                Choose Realme GT if you want maximum performance and don't mind spending more. 
                Choose OnePlus Nord if you want great gaming at a lower price point."
                
                WHAT NOT TO DO:
                ❌ Don't give 2-3 line answers for detailed questions
                ❌ Don't write essay-length answers (keep it focused)
                ❌ Don't ask follow-up questions like "What's your budget?" if already specified
                ❌ Don't be vague - always mention specific models and reasons
                
                REMEMBER: Medium-detailed, clear, helpful, straight to the point.
            """.trimIndent()
        }
    }

    private fun buildRequestBody(systemPrompt: String, userMessage: String): String {
        val requestJson = JSONObject()
        val contentsArray = org.json.JSONArray()
        
        // System prompt as first message
        val systemPart = JSONObject()
        systemPart.put("text", systemPrompt)
        
        val systemMessage = JSONObject()
        systemMessage.put("role", "user")
        systemMessage.put("parts", org.json.JSONArray().put(systemPart))
        contentsArray.put(systemMessage)
        
        // User message
        val userPart = JSONObject()
        userPart.put("text", userMessage)
        
        val userMessageObj = JSONObject()
        userMessageObj.put("role", "user")
        userMessageObj.put("parts", org.json.JSONArray().put(userPart))
        contentsArray.put(userMessageObj)
        
        requestJson.put("contents", contentsArray)
        
        // Add generation config
        val generationConfig = JSONObject()
        generationConfig.put("temperature", 0.7)
        generationConfig.put("topK", 40)
        generationConfig.put("topP", 0.95)
        generationConfig.put("maxOutputTokens", 1024)
        requestJson.put("generationConfig", generationConfig)
        
        return requestJson.toString()
    }

    private fun makeApiRequest(requestBody: String): String {
        val url = URL("$API_URL?key=$API_KEY")
        val connection = url.openConnection() as HttpsURLConnection
        
        try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
            
            Log.d(TAG, "Sending request to: $API_URL")
            
            // Send request
            connection.outputStream.use { output ->
                output.write(requestBody.toByteArray())
                output.flush()
            }
            
            // Read response
            val responseCode = connection.responseCode
            Log.d(TAG, "Response code: $responseCode")
            
            val inputStream = if (responseCode == HttpsURLConnection.HTTP_OK) {
                connection.inputStream
            } else {
                Log.w(TAG, "Non-200 response code: $responseCode")
                connection.errorStream
            }
            
            val responseText = inputStream.bufferedReader().use { it.readText() }
            Log.d(TAG, "Raw response: ${responseText.take(500)}")
            return responseText
        } catch (e: Exception) {
            Log.e(TAG, "API request failed: ${e.message}", e)
            throw e
        } finally {
            connection.disconnect()
        }
    }

    private fun parseResponse(response: String): String {
        return try {
            val jsonResponse = JSONObject(response)
            
            // Check for error in response
            if (jsonResponse.has("error")) {
                val error = jsonResponse.getJSONObject("error")
                val errorMessage = error.optString("message", "Unknown error")
                Log.e(TAG, "API Error: $errorMessage")
                return "API Error: $errorMessage"
            }
            
            val candidates = jsonResponse.optJSONArray("candidates")
            
            if (candidates != null && candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val content = firstCandidate.getJSONObject("content")
                val parts = content.getJSONArray("parts")
                
                if (parts.length() > 0) {
                    val firstPart = parts.getJSONObject(0)
                    val text = firstPart.getString("text")
                    Log.d(TAG, "Successfully parsed response: ${text.take(100)}")
                    text
                } else {
                    Log.w(TAG, "No parts in response")
                    "No response generated. Please try again."
                }
            } else {
                Log.w(TAG, "No candidates in response")
                "No response generated. Please try again."
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing response: ${e.message}", e)
            "Error parsing response: ${e.message}"
        }
    }
}
