package com.staxter.uam.utility;

import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 * 
 * Thew utility class with methods
 * @author Veena ANil
 *
 */
@Service
public class Utils {

	public String generateUserId() {
		return UUID.randomUUID().toString();
	}

}
