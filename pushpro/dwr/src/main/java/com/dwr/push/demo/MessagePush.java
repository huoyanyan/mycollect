package com.dwr.push.demo;

import java.util.Collection;

import javax.servlet.ServletException;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

/**
 * https://blog.csdn.net/pangliang_csdn/article/details/68945872
 * 
 * @author zhangfei
 *
 */
public class MessagePush {

	public void onPageLoad(String userId) {

		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();

		scriptSession.setAttribute("userId", userId); // 把前台传入的id保存

		DwrScriptSessionManagerUtil dwrScriptSessionManagerUtil = new DwrScriptSessionManagerUtil();

		try {

			dwrScriptSessionManagerUtil.init();

		} catch (ServletException e) {

			e.printStackTrace();

		}

	}

	private void sendToOnePerson() {
		// 修改成功给张三发消息
		final String userId = "2"; // 这个我写死张三userId=2
		final String autoMessage = "上级已经更改了这条线索的归属人了！";
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) { // 验证符合条件的发送人
				if (session.getAttribute("userId") == null) {
					return false;
				} else {
					return (session.getAttribute("userId")).equals(userId);
				}
			}
		}, new Runnable() {

			private ScriptBuffer script = new ScriptBuffer();

			public void run() {

				script.appendCall("showMessage", autoMessage);

				Collection<ScriptSession> sessions = Browser.getTargetSessions();

				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
}
