package com.alex.telegram.bot.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.alex.telegram.bot.config.AuemuraBot;
import com.alex.telegram.bot.dto.OfertaDTO;

import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class BotService {
	
	public SendMessage execute(long chat_id, Update update) {
		
		SendMessage message = new SendMessage();
		
		if(update.getMessage().getText() != null && update.getMessage().getText().equalsIgnoreCase("/start")) {
	        message = new SendMessage() 
	                .setChatId(chat_id)
	                .setText("O que deseja saber hoje?");
		} else if(update.getMessage().getText() != null && update.getMessage().getText().contains("oferta")) {
			message = getLocation(update);
		} else if(update.getMessage().getText() == null && update.getMessage().getLocation() != null) {
			Double latitude = Double.parseDouble(update.getMessage().getLocation().getLatitude().toString());
			Double longitude = Double.parseDouble(update.getMessage().getLocation().getLongitude().toString());
			IntegrationService integrationService = new IntegrationService();
			List<OfertaDTO> list = integrationService.execute(latitude, longitude);
			StringBuilder sb = new StringBuilder();
			list.forEach(o -> {
				sb.append(o.getNomeProduto())
					.append(o.getPreco())
					.append("\n");
			});
	        message = new SendMessage()
	                .setChatId(chat_id)
	                .setText(sb.toString());
		}
		return message;
	}
	
	public SendMessage getLocation(Update update) {
        long chat_id = update.getMessage().getChatId();

        SendMessage message = new SendMessage() 
                .setChatId(chat_id)
                .setText("Por favor, compartilhe sua localização.");
        
        ReplyKeyboardMarkup keyMarkup = new ReplyKeyboardMarkup();
        keyMarkup.setOneTimeKeyboard(true);
        keyMarkup.setResizeKeyboard(true);
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Compartilhar minha localização").setRequestLocation(true);
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(keyboardButton);
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        keyboard.add(keyboardRow);
        keyMarkup.setKeyboard(keyboard);
        
        return message.setReplyMarkup(keyMarkup);
	}

}
