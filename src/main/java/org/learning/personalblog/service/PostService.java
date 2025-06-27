package org.learning.personalblog.service;

import org.learning.personalblog.dto.PostDto; // We'll define this DTO next
import org.learning.personalblog.entity.Post;
import org.learning.personalblog.entity.User;
import org.learning.personalblog.repository.PostRepository;
import org.learning.personalblog.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates and saves a new post.
     *
     * @param postDto The DTO containing the title and content of the new post.
     * @param username The username of the author, retrieved from the security context.
     * @return The saved Post entity.
     */
    @Transactional // Ensures the whole method runs in a single database transaction
    public Post createPost(PostDto postDto, String username) {
        // 1. Find the author (User) from the database
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 2. Create a new Post entity
        Post newPost = new Post();
        newPost.setTitle(postDto.getTitle());
        newPost.setContent(postDto.getContent());
        newPost.setUser(author); // 3. Associate the post with the author

        // 4. Save the Post entity using the repository.
        // The 'createdAt' and 'updatedAt' fields will be automatically populated by Hibernate.
        return postRepository.save(newPost);
    }
}