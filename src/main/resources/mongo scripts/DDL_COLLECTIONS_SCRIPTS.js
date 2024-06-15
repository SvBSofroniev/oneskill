db.createCollection("Users", {
   validator: {
      $jsonSchema: {
         bsonType: "object",
         required: ["username", "email", "password", "roles", "created_at", "firstname", "lastname", "updated_at"],
         properties: {
            username: {
               bsonType: "string",
               description: "User's name"
            },
            email: {
               bsonType: "string",
               pattern: "^.+@.+$",
               description: "User's email"
            },
            password: {
               bsonType: "string",
               description: "Hashed password"
            },
            roles: {
               bsonType: "array",
               description: "List of roles for the user",
               items: {
                  bsonType: "string",
                  enum: ["user", "dev"]
               }
            },
            created_at: {
               bsonType: "string",
               description: "Timestamp of account creation"
            },
            updated_at: {
               bsonType: "string",
               description: "Timestamp of last account update"
            },
            firstname: {
               bsonType: "string",
               description: "User's first name"
            },
            lastname: {
               bsonType: "string",
               description: "User's last name"
            }
         }
      }
   }
});
db.Users.createIndex({ username: 1 }, { unique: true });
db.Users.createIndex({ email: 1 }, { unique: true });

db.createCollection("Videos", {
   validator: {
      $jsonSchema: {
         bsonType: "object",
         required: ["username", "title", "description", "video_data", "thumbnail_data", "upload_date", "status", "views", "likes", "dislikes", "shared_count"],
         properties: {
            username: {
               bsonType: "string",
               description: "Identifier of the user who uploaded the video"
            },
            title: {
               bsonType: "string",
               description: "Title of the video"
            },
            description: {
               bsonType: "string",
               description: "Description of the video"
            },
            video_data: {
               bsonType: "binData",
               description: "Binary data of the video file"
            },
            thumbnail_data: {
               bsonType: "binData",
               description: "Binary data of the video thumbnail"
            },
            upload_date: {
               bsonType: "string",
               description: "Timestamp of when the video was uploaded"
            },
            status: {
               enum: ["active", "suspended"],
               description: "Status of the video (e.g., 'active', 'suspended')"
            },
            views: {
               bsonType: "int",
               description: "Number of views",
               minimum: 0
            },
            likes: {
               bsonType: "int",
               description: "Number of likes",
               minimum: 0
            },
            dislikes: {
               bsonType: "int",
               description: "Number of dislikes",
               minimum: 0
            },
            shared_count: {
               bsonType: "int",
               description: "Number of times the video has been shared",
               minimum: 0
            }
         }
      }
   }
});

db.Videos.createIndex({ title: 1 }, { unique: true });
db.Videos.createIndex({ username: 1 });


db.createCollection("Comments", {
   validator: {
      $jsonSchema: {
         bsonType: "object",
         required: ["comment_id", "video_id", "user_id", "content", "timestamp"],
         properties: {
            comment_id: {
               bsonType: "string",
               description: "Unique identifier for the comment"
            },
            video_id: {
               bsonType: "string",
               description: "Identifier of the video being commented on"
            },
            user_id: {
               bsonType: "string",
               description: "Identifier of the user who made the comment"
            },
            content: {
               bsonType: "string",
               description: "The comment text"
            },
            timestamp: {
               bsonType: "string",
               description: "When the comment was made"
            }
         }
      }
   }
});

db.Comments.createIndex({ comment_id: 1 }, { unique: true });
db.Comments.createIndex({ video_id: 1 });
db.Comments.createIndex({ user_id: 1 });

db.createCollection("Ratings", {
   validator: {
      $jsonSchema: {
         bsonType: "object",
         required: ["rating_id", "video_id", "user_id", "rating", "timestamp"],
         properties: {
            rating_id: {
               bsonType: "string",
               description: "Unique identifier for the rating"
            },
            video_id: {
               bsonType: "string",
               description: "Identifier of the video being rated"
            },
            user_id: {
               bsonType: "string",
               description: "Identifier of the user who made the rating"
            },
            rating: {
               bsonType: "int",
               minimum: 1,
               maximum: 5,
               description: "Rating value (e.g., 1 to 5 stars)"
            },
            timestamp: {
               bsonType: "string",
               description: "When the rating was made"
            }
         }
      }
   }
});
db.Ratings.createIndex({ rating_id: 1 }, { unique: true });
db.Ratings.createIndex({ video_id: 1 });
db.Ratings.createIndex({ user_id: 1 });


db.createCollection("LikesDislikes", {
   validator: {
      $jsonSchema: {
         bsonType: "object",
         required: ["like_id", "video_id", "user_id", "type", "timestamp"],
         properties: {
            like_id: {
               bsonType: "string",
               description: "Unique identifier for the like/dislike"
            },
            video_id: {
               bsonType: "string",
               description: "Identifier of the video being liked/disliked"
            },
            user_id: {
               bsonType: "string",
               description: "Identifier of the user who liked/disliked"
            },
            type: {
               enum: ["like", "dislike"],
               description: "Type of interaction (e.g., 'like', 'dislike')"
            },
            timestamp: {
               bsonType: "string",
               description: "When the like/dislike was made"
            }
         }
      }
   }
});

db.LikesDislikes.createIndex({ like_id: 1 }, { unique: true });
db.LikesDislikes.createIndex({ video_id: 1 });
db.LikesDislikes.createIndex({ user_id: 1 });


db.createCollection("Shares", {
   validator: {
      $jsonSchema: {
         bsonType: "object",
         required: ["share_id", "video_id", "user_id", "timestamp", "platform"],
         properties: {
            share_id: {
               bsonType: "string",
               description: "Unique identifier for the share"
            },
            video_id: {
               bsonType: "string",
               description: "Identifier of the video being shared"
            },
            user_id: {
               bsonType: "string",
               description: "Identifier of the user who shared the video"
            },
            timestamp: {
               bsonType: "string",
               description: "When the video was shared"
            },
            platform: {
               bsonType: "string",
               description: "Platform where the video was shared (e.g., 'email', 'social_media')"
            }
         }
      }
   }
});
db.Shares.createIndex({ share_id: 1 }, { unique: true });
db.Shares.createIndex({ video_id: 1 });
db.Shares.createIndex({ user_id: 1 });


db.createCollection("RolesPermissions", {
   validator: {
      $jsonSchema: {
         bsonType: "object",
         required: ["role_id", "role_name", "permissions"],
         properties: {
            role_id: {
               bsonType: "string",
               description: "Unique identifier for the role"
            },
            role_name: {
               bsonType: "string",
               description: "Name of the role (e.g., 'dev', 'user')"
            },
            permissions: {
               bsonType: "array",
               items: {
                  bsonType: "string",
                  description: "List of permissions assigned to the role"
               },
               description: "List of permissions assigned to the role (e.g., ['upload_video', 'delete_own_video', 'manage_accounts'])"
            }
         }
      }
   }
});

db.RolesPermissions.createIndex({ role_id: 1 }, { unique: true });
db.RolesPermissions.createIndex({ role_name: 1 });

