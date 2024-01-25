package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.syndicate.deployment.annotations.LambdaUrlConfig;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.lambda.url.AuthType;
import com.syndicate.deployment.model.lambda.url.InvokeMode;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(lambdaName = "hello_world",
	roleName = "hello_world-role",
	isPublishVersion = true,
	aliasName = "${lambdas_alias_name}"
)

@LambdaUrlConfig(
		authType = AuthType.NONE,
		invokeMode = InvokeMode.BUFFERED
)
public class HelloWorld implements RequestHandler<Map<String,Object>, String> {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public String handleRequest(Map<String, Object> event, Context context) {
		//create a map to store your results
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("statusCode", 200);
		responseMap.put("message", "Hello from Lambda");
		String jsonResult = gson.toJson(responseMap);
		//return a json string
		return jsonResult;
	}
}
