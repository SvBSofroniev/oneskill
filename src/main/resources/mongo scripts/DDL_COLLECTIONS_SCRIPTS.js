use oneskill

db.Users.drop();
db.Videos.drop();
db.Thumbnails.drop();
db.Comments.drop();
db.Ratings.drop();
db.LikesDislikes.drop();
db.Shares.drop();
db.RolesPermissions.drop();
db.EnrolledVideo.drop();

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
                  enum: ["user", "dev", "lector", "admin"]
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
db.Users.createIndex({ enrolled: 1 });


db.createCollection("Videos", {
   validator: {
      $jsonSchema: {
         bsonType: "object",
         required: ["username", "title", "description", "upload_date", "status", "views", "likes", "dislikes", "shared_count"],
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
            video_id: {
               bsonType: "objectId",
               description: "ObjectId of the video file stored in GridFS"
            },
            thumbnail_id: {
               bsonType: "objectId",
               description: "ObjectId of the video thumbnail stored in GridFS"
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

db.createCollection("EnrolledVideo", {
   validator: {
      $jsonSchema: {
         bsonType: "object",
         required: ["username", "videoId"],
         properties: {
            username: {
               bsonType: "string",
               description: "ObjectId of the user who enrolled in the video"
            },
            videoId: {
               bsonType: "string",
               description: "ObjectId of the video that the user enrolled in"
            }
         }
      }
   }
});
db.EnrolledVideo.createIndex({ username: 1, videoId: 1 }, { unique: true });

db.createCollection("Comments", {
   validator: {
      $jsonSchema: {
         bsonType: "object",
         required: ["video_id", "username", "content", "timestamp"],
         properties: {
            video_id: {
               bsonType: "string",
               description: "Identifier of the video being commented on"
            },
            username: {
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
         required: ["video_id", "username", "type"],
         properties: {
            video_id: {
               bsonType: "string",
               description: "Identifier of the video being liked/disliked"
            },
            username: {
               bsonType: "string",
               description: "Identifier of the user who liked/disliked"
            },
            type: {
               enum: ["like", "dislike", "none"],
               description: "Type of interaction (e.g., 'like', 'dislike')"
            }
         }
      }
   }
});
db.LikesDislikes.createIndex(
   { username: 1, video_id: 1 },
   { unique: true }
);



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

