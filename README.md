# M Board

A real time messaging platform backend built with Spring Boot 4.

## Features

- [x] User authorization
- [x] Database
- [x] Chat rooms
- [x] Typing indicator
- [x] Active user count
- [x] Paginated message history
- [ ] Reply System
- [ ] User page
- [ ] Profile picture
- [ ] Custom status
- [x] DTO Input validation
- [ ] Proper frontend

## Installation and Usage

### Prerequisites

**Java 21**

**Git**

### Installation

```bash
git clone https://github.com/randomboi404/mboard
cd mboard
./mvnw spring-boot:run
```

## API Endpoints

### Authentication & Registration
| Method | Path | Description | Auth Required |
| :--- | :--- | :--- | :---: |
| **GET** | `/register` | Redirects to registration page | No |
| **POST** | `/api/register` | Register a new user account | No |
| **GET** | `/login` | Login to an existing account | No |

**Registration Payload**
| Field | Constraints |
| :--- | :--- |
| `username` | Not blank, 3-14 chars, alphanumeric/underscores |
| `password` | Not blank, 6-24 chars |

---

### Conversations
| Method | Path | Description | Auth Required |
| :--- | :--- | :--- | :---: |
| **GET** | `/api/chat` | List all conversations for the current user | Yes |
| **POST** | `/api/chat` | Create a new conversation | Yes |
| **GET** | `/api/chat/{id}` | Get metadata for a specific conversation | Yes |

**Create Conversation Payload**
| Field | Constraints |
| :--- | :--- |
| `title` | Not blank, 1-20 chars |
| `participantIds` | Non-empty set of user IDs |

---

### Messages
| Method | Path | Description | Auth Required |
| :--- | :--- | :--- | :---: |
| **GET** | `/chat/{conversationId}` | Get paginated messages list | Yes |
| **POST** | `/chat/{conversationId}` | Send a message | Yes |

**Message Payload**
| Field | Constraints |
| :--- | :--- |
| `content` | Not blank, max 3000 chars |

---

### Real-Time Notifications (SSE)
| Method | Path | Description | Auth Required |
| :--- | :--- | :--- | :---: |
| **GET** | `/api/chat/{conversationId}/stream` | Subscribe to SSE stream | Yes |
| **GET** | `/api/chat/{conversationId}/users/active` | Get active user count | Yes |
| **POST** | `/api/chat/{conversationId}/typing` | Broadcast typing status | Yes |
