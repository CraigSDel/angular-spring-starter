# Backend Fix Guide - Spring Security 6 Migration

## Problem Summary
The backend is failing to compile because `WebSecurityConfig.java` uses deprecated Spring Security 5 APIs that were removed in Spring Security 6 (Spring Boot 3).

## Error Details
```
cannot find symbol: class WebSecurityConfigurerAdapter
cannot find symbol: method ignoringAntMatchers(...)
```

## Root Cause
Spring Security 6 removed:
- `WebSecurityConfigurerAdapter` (use component-based configuration)
- `.ignoringAntMatchers()` (use `.ignoringRequestMatchers()`)
- Old lambda-less configuration style

## Solution

### Option 1: Clean Build (Recommended)
The file has already been updated to Spring Security 6 syntax. Just clean and rebuild:

```bash
cd server

# Kill any running Maven processes
pkill -f "mvnw" 2>/dev/null

# Clean Maven cache
./mvnw clean

# Set Java 17
export JAVA_HOME=/Users/craigstroberg/Library/Java/JavaVirtualMachines/corretto-17.0.12/Contents/Home

# Verify Java version
java -version  # Should show Java 17

# Run the application
./mvnw spring-boot:run
```

### Option 2: Verify File Contents
If the above doesn't work, verify the file was properly updated:

```bash
# Check if file contains new Spring Security 6 syntax
grep "SecurityFilterChain" server/src/main/java/com/bfwg/config/WebSecurityConfig.java

# Should output line containing: public SecurityFilterChain filterChain
```

If the grep returns nothing, the file wasn't updated. Manually verify it contains:
- `@EnableWebSecurity` instead of extending `WebSecurityConfigurerAdapter`
- `SecurityFilterChain filterChain(HttpSecurity http)` bean method
- Lambda-style configuration: `csrf(csrf -> ...)`

### Option 3: Force File Update
If Maven is caching the old file:

```bash
cd server

# Remove target directory
rm -rf target/

# Clean Maven local repo cache for this project
rm -rf ~/.m2/repository/com/bfwg/

# Rebuild
export JAVA_HOME=/Users/craigstroberg/Library/Java/JavaVirtualMachines/corretto-17.0.12/Contents/Home
./mvnw clean compile spring-boot:run
```

## What Was Changed

### Old Code (Spring Security 5)
```java
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().ignoringAntMatchers("/api/login", "/api/signup")
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        // ...
  }
}
```

### New Code (Spring Security 6)
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .ignoringRequestMatchers("/api/login", "/api/signup")
      )
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      // ...
    return http.build();
  }
}
```

## Verification Steps

1. **Check Compilation**
```bash
cd server
./mvnw clean compile
# Should complete without errors
```

2. **Start Backend**
```bash
export JAVA_HOME=/Users/craigstroberg/Library/Java/JavaVirtualMachines/corretto-17.0.12/Contents/Home
./mvnw spring-boot:run
```

3. **Look for Success Message** (in logs)
```
Started Application in X.XXX seconds
Tomcat started on port 8080
```

4. **Test API Endpoint**
```bash
curl http://localhost:8080/api/foo
# Should return: {"foo":"bar"}
```

## Common Issues

### Issue: "Port 8080 already in use"
```bash
# Find and kill process on port 8080
lsof -ti:8080 | xargs kill -9
```

### Issue: Wrong Java version
```bash
# Check Java version
java -version

# Set correct Java home
export JAVA_HOME=/Users/craigstroberg/Library/Java/JavaVirtualMachines/corretto-17.0.12/Contents/Home

# Verify
$JAVA_HOME/bin/java -version
```

### Issue: Maven wrapper permissions
```bash
chmod +x server/mvnw
```

## Complete Startup Sequence

```bash
# Terminal 1 - Backend
cd /Users/craigstroberg/myworkspace/git/angular-spring-starter/server
export JAVA_HOME=/Users/craigstroberg/Library/Java/JavaVirtualMachines/corretto-17.0.12/Contents/Home
./mvnw clean spring-boot:run

# Wait for "Started Application" message

# Terminal 2 - Frontend (already running)
# Frontend should already be running on port 4200
# If not:
cd /Users/craigstroberg/myworkspace/git/angular-spring-starter/frontend
npm start
```

## Test Application

Once both servers are running:

1. **Open Frontend**: http://localhost:4200
2. **Test Login**:
   - Admin: `admin` / `123`
   - User: `user` / `123`

3. **Test API Directly**:
```bash
# Public endpoint (no auth)
curl http://localhost:8080/api/foo

# Protected endpoint (requires auth)
curl http://localhost:8080/api/whoami
```

## Database Access

The application uses H2 in-memory database:
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: _(leave empty)_

## Commit Changes

Once backend is working:

```bash
cd /Users/craigstroberg/myworkspace/git/angular-spring-starter

# Stage all changes
git add -A

# Commit
git commit -m "Fix Spring Security 6 configuration and Angular routing

- Updated WebSecurityConfig for Spring Boot 3
- Removed deprecated WebSecurityConfigurerAdapter
- Migrated to SecurityFilterChain bean pattern
- Fixed ignoringAntMatchers -> ignoringRequestMatchers
- Updated Maven wrapper to 3.9.9
- Fixed Angular routing relativeLinkResolution deprecation"

# Push
git push origin feature/upgrade_steps
```

## Troubleshooting Logs

Check logs if issues persist:

```bash
# Backend logs
tail -f server/backend.log

# Or if running in foreground, logs appear in terminal
```

## Next Steps After Fix

1. ✅ Verify both frontend and backend are running
2. ✅ Test login functionality
3. ✅ Test API endpoints
4. ✅ Commit and push all changes
5. ✅ Run tests: `cd server && ./mvnw test`
6. ✅ Run frontend tests: `cd frontend && npm test`

## Support

If issues persist:
- Check `server/backend.log` for detailed errors
- Verify Java 17 is installed and JAVA_HOME is set correctly
- Ensure all files are saved (not just in editor buffer)
- Try a full system restart if compilation cache is corrupted

