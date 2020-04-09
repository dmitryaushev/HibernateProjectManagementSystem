package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.model.Developer;

public class GetDeveloper implements Command {

    private View view;
    private DeveloperDAO developerDAO;

    public GetDeveloper(View view, DeveloperDAO developerDAO) {
        this.view = view;
        this.developerDAO = developerDAO;
    }

    @Override
    public String command() {
        return "Get developer";
    }

    @Override
    public void process() {

        view.write("Enter a developer id");
        int developerID = Integer.parseInt(view.read());
        Developer developer = developerDAO.getByID(developerID);

        if (developer == null) {
            throw new IllegalArgumentException(String.format("Developer with id %d not exist", developerID));
        }

        view.write(developer.toString());
        sleep();
    }
}
