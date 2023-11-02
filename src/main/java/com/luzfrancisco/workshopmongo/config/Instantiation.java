package com.luzfrancisco.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.luzfrancisco.workshopmongo.domain.Post;
import com.luzfrancisco.workshopmongo.domain.User;
import com.luzfrancisco.workshopmongo.dto.AuthorDTO;
import com.luzfrancisco.workshopmongo.dto.CommentDTO;
import com.luzfrancisco.workshopmongo.repository.PostRepository;
import com.luzfrancisco.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userReposiroty;

	@Autowired
	private PostRepository postReposiroty;

	@Override
	public void run(String... arg0) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userReposiroty.deleteAll();
		postReposiroty.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userReposiroty.saveAll(Arrays.asList(maria, alex, bob));

		Post post1 = new Post(null, sdf.parse("21/03/2023"), "Off you go", "I'm travelling to São Paulo, cheers!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2023"), "Good morning", "I woke up happy today!", new AuthorDTO(maria));

		CommentDTO c1 = new CommentDTO("Have a good trip, bro!", sdf.parse("21/03/2023"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Enjoy", sdf.parse("22/03/2023"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Have a great day!", sdf.parse("23/03/2023"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postReposiroty.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userReposiroty.save(maria);
	}

}