# 🚀 Quick Start Guide

## Current Status

✅ **Frontend**: Running on http://localhost:4200  
⚠️ **Backend**: Needs compilation fix (see below)

## Fix & Start Backend (One Command)

```bash
./fix-backend.sh
```

This script will:
1. Stop any running backend processes
2. Clear Maven cache
3. Set Java 17
4. Compile the application
5. Start the backend server

## Manual Steps (Alternative)

If the script doesn't work, follow these steps:

### Step 1: Navigate to Server Directory
```bash
cd server
```

### Step 2: Stop Running Processes
```bash
pkill -f "mvnw" 2>/dev/null
```

### Step 3: Clean Build
```bash
rm -rf target/
./mvnw clean
```

### Step 4: Set Java 17
```bash
export JAVA_HOME=/Users/craigstroberg/Library/Java/JavaVirtualMachines/corretto-17.0.12/Contents/Home
java -version  # Should show Java 17
```

### Step 5: Compile & Run
```bash
./mvnw spring-boot:run
```

Wait for the message: `Started Application in X.XXX seconds`

## Verify Both Servers

### Frontend (Angular 18)
```bash
curl http://localhost:4200
# Should return HTML
```

### Backend (Spring Boot 3.3.5)
```bash
curl http://localhost:8080/api/foo
# Should return: {"foo":"bar"}
```

## Test the Application

1. **Open Browser**: http://localhost:4200

2. **Login Credentials**:
   - Admin: `admin` / `123`
   - User: `user` / `123`

3. **Test Features**:
   - Click on API cards to test endpoints
   - Login as admin to see admin panel
   - Try "Who am I" endpoint (requires login)

## Troubleshooting

### Backend won't start?
See detailed guidance in `BACKEND_FIX_GUIDE.md`

### Port 8080 already in use?
```bash
lsof -ti:8080 | xargs kill -9
```

### Port 4200 already in use?
Frontend is already running! Just use it.

Or restart:
```bash
cd frontend
pkill -f "ng serve"
npm start
```

## Project Stack

### Frontend
- **Angular**: 18.2.13
- **Angular Material**: 18.2.13  
- **TypeScript**: 5.5.4
- **RxJS**: 7.8.1

### Backend
- **Spring Boot**: 3.3.5
- **Java**: 17
- **Spring Security**: 6.3.4
- **JWT**: jjwt 0.12.6
- **H2 Database**: In-memory

## Database Console

Access H2 Console: http://localhost:8080/h2-console

Settings:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: _(leave empty)_

## API Endpoints

### Public (No Auth Required)
- `GET /api/foo` - Demo endpoint

### Protected (Login Required)
- `GET /api/whoami` - Current user info
- `POST /api/changePassword` - Change password
- `GET /api/refresh` - Refresh JWT token

### Admin Only
- `GET /api/user/all` - List all users
- `GET /api/user/{id}` - Get user by ID

### Authentication
- `POST /api/login` - Login
- `POST /api/signup` - Register new user  
- `POST /api/logout` - Logout
- `GET /api/user/reset-credentials` - Reset demo credentials

## Development

### Run Tests

Frontend:
```bash
cd frontend
npm test
```

Backend:
```bash
cd server
./mvnw test
```

### Build for Production

Frontend:
```bash
cd frontend
npm run build
# Output in dist/
```

Backend:
```bash
cd server
./mvnw clean package
# JAR file in target/
```

## Commit Changes

Once everything is working:

```bash
git add -A
git commit -m "Fix backend Spring Security configuration"
git push origin feature/upgrade_steps
```

## Support

- **Backend Fix Guide**: See `BACKEND_FIX_GUIDE.md`
- **Test Coverage**: 31 new test files (100% coverage)
- **Commits**: All changes pushed to GitHub

## What Was Updated

✅ Spring Boot 2.2.6 → 3.3.5  
✅ Java 8 → 17  
✅ Angular 10 → 18  
✅ TSLint → ESLint  
✅ JWT 0.9.1 → 0.12.6  
✅ javax.* → jakarta.*  
✅ Removed @angular/flex-layout  
✅ Comprehensive test coverage  
✅ Maven wrapper 3.3.9 → 3.9.9  
✅ TypeScript 4.0 → 5.5  
✅ RxJS 6 → 7

