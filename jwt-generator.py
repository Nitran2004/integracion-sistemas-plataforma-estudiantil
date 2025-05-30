import jwt
import datetime
import json

# Configuración
SECRET_KEY = "mi-clave-secreta-super-segura-para-jwt-token-que-debe-ser-muy-larga"
ISSUER = "universidad-issuer"

def generate_jwt_token(user_id, role="student", expires_hours=24):
    """Genera un token JWT para pruebas"""
    
    # Payload del token
    payload = {
        'iss': ISSUER,  # Issuer
        'sub': user_id,  # Subject (user ID)
        'role': role,    # Rol del usuario
        'iat': datetime.datetime.utcnow(),  # Issued at
        'exp': datetime.datetime.utcnow() + datetime.timedelta(hours=expires_hours)  # Expiration
    }
    
    # Generar token
    token = jwt.encode(payload, SECRET_KEY, algorithm='HS256')
    
    return token

if __name__ == "__main__":
    # Generar tokens de prueba
    tokens = {
        "student_token": generate_jwt_token("EST001", "student"),
        "admin_token": generate_jwt_token("ADM001", "admin"),
        "teacher_token": generate_jwt_token("DOC001", "teacher")
    }
    
    print("=== TOKENS JWT GENERADOS ===")
    for token_name, token_value in tokens.items():
        print(f"\n{token_name}:")
        print(token_value)