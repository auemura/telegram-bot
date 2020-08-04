package com.alex.telegram.bot.config;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.alex.telegram.bot.service.BotService;
@Component
public class AuemuraBot extends TelegramLongPollingBot{
	
	private static final Logger logger = LogManager.getLogger(AuemuraBot.class);
	
	@PostConstruct
	public void initializrConfig(){
	     TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
	     try {
	       telegramBotsApi.registerBot(this);
	     } catch (TelegramApiException e) {
	    	 logger.error(e.getMessage());
	     }
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		
		long chat_id = update.getMessage().getChatId();
		
		try {
			BotService botService = new BotService();
			execute(botService.execute(chat_id, update)); 
		} catch (TelegramApiException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public String getBotUsername() {
		return ReadProperties.getProperty("app.alex.telegram.bot-name");
	}

	@Override
	public String getBotToken() {
		return ReadProperties.getProperty("app.alex.telegram.bot-token");
	}

}
