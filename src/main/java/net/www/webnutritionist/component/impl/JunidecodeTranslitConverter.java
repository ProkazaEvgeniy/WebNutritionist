package net.www.webnutritionist.component.impl;

import org.springframework.stereotype.Component;

import net.sf.junidecode.Junidecode;
import net.www.webnutritionist.component.TranslitConverter;

@Component
public class JunidecodeTranslitConverter implements TranslitConverter {

	@Override
	public String translit(String text) {
		return Junidecode.unidecode(text);
	}
}
