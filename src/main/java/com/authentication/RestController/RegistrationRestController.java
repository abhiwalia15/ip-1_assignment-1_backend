package com.authentication.RestController;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.authentication.POJO.User;
import com.authentication.Repository.UserRepository;
import com.authentication.Service.RegistrationService;


@RestController
public class RegistrationRestController {
	
	@Autowired
	UserRepository userrepo;
	
	@RequestMapping(value = "/rest/registration", method = RequestMethod.POST, consumes = "multipart/form-data")
	public String RegistraionRest(User user, @RequestParam(value = "image", required = false) MultipartFile imageFile,HttpServletRequest req) {
		JSONObject res = new JSONObject();
		res.put("message", "error");
		
		byte[] image = null;
		try {
			RegistrationService regisService = new RegistrationService();

			if (regisService.ValidateData(user)) {

				regisService.setRequest(req);
								
				if (regisService.CheckForDuplicate(user, userrepo)) {
					
					if(imageFile!=null && imageFile.getBytes().length>0) {
						image = imageFile.getBytes();
					}
					
					if (regisService.InsertRegistraionData(user, image, userrepo)) {
						res.put("message", "Your are successfully register.");
						res.put("status", "success");
					} else {
						res.put("message", "There is problem in registeration.");
						res.put("status", "failure");
					}
				
				} else {
					res.put("message", regisService.getMessage());
					res.put("status", "failure");
				}
			} else {
				res.put("message", regisService.getMessage());
				res.put("status", "failure");

			}

		} catch (Exception e) {
			System.out.println("Exception in RegistraionRest" + e);
			// TODO: handle exception
		}
		
		return res.toString();
		
	}
			
}
