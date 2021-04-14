package com.example.application.views.chat;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.charts.model.TimeUnit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.vaadin.artur.Avataaar;

import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "chat", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("chat")
@CssImport("./views/chat/chat-view.css")

public class ChatView extends Div {

//    public ChatView() {
//        addClassName("chat-view");
//        add(new Text("Content placeholder"));
//    }
	private final UI ui;
	private final MessageList messageList = new MessageList();
	private final TextField message = new TextField();
	private final Chat chatSession;
	private final ScheduledExecutorService executorService;
	
	public ChatView(Bot alice, ScheduledExecutorService executorService) {
		
		    this.executorService = executorService;
		    ui = UI.getCurrent();
		    chatSession = new Chat(alice);
		    message.setPlaceholder("Enter a message...");
		    message.setSizeFull();
		    Button send = new Button(VaadinIcon.ENTER.create(), event -> sendMessage());
		    send.addClickShortcut(Key.ENTER);
		    HorizontalLayout inputLayout = new HorizontalLayout(message, send);
		    System.out.println("PASSOU POR AQUI!");
		    inputLayout.addClassName("inputLayout");
		    add(messageList, inputLayout);
//		    expand(messageList);
		    setSizeFull();
	}
	
	private void sendMessage() {
		
	    String text = message.getValue();
	    System.out.println(text);
	    messageList.addMessage("You", new Avataaar("Name"), text, true);
	    message.clear();
        executorService.schedule(() -> {
            String answer = chatSession.multisentenceRespond(text);
            System.out.println(answer);
            ui.access(() -> messageList.addMessage(
                    "Alice", new Avataaar("Alice2"), answer, false));
        },new Random().ints(1000, 3000).findFirst().getAsInt(),TimeUnit.MILLISECONDS);
//        System.out.println("APENAS UM TESTE");
	}
	
}
