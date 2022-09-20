package com.authentication.RestController;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.Repository.UserRepository;

@RestController
public class BlockUserAccountRestController {

	
	@Autowired
	UserRepository userRepo;

	@RequestMapping(value = "/rest/blockUserAccountRestController", method = RequestMethod.POST, consumes = "multipart/form-data")
	public String UpdateUserInfo(String email, HttpServletRequest req) {
		JSONObject json = new JSONObject();
		json.put("status", "failure");
		json.put("message", "Error");
		try {
			System.out.println("Email: " + email);
			
			int cnt = userRepo.blockUser(email);
			if (cnt > 0) {
				json.put("status", "success");
				json.put("message", "Account blocked successfully!");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception e" + e);
		}
		return json.toString();
	}
}
