from fastapi import APIRouter, Response, Depends
from fastapi.security import OAuth2PasswordBearer, OAuth2PasswordRequestForm
from passlib.context import CryptContext
from jose import jwt, JWTError

from decouple import config
from datetime import datetime, timedelta

from schemas.schemas import schema_user, schema_user_db
from models.base_models import BUser, BUserDb, BToken
from models.models import User
from db.connection import session
from db.utils_generic import id_ok

router = APIRouter(prefix="/users", 
                   tags = ["users"],
                # dependencies=[Depends(get_token_header)],
                   responses={404: {"message": "No encontrado"}})

# SECRET_KEY = config("SECRET_KEY")

SECRET_KEY = "c74f2119477cd313948f6f1fd918bc60edcb5d8ea77d12b97f373415cb378ea7"
ALGORITHM = config("ALGORITHM")

oauth2_scheme = OAuth2PasswordBearer("users/token")
password_context = CryptContext(schemes=["bcrypt"], deprecated="auto")

def verify_password(plane_password, hashed_password ):
    return password_context.verify(plane_password, hashed_password)

def get_user(username):
    user = session.query(User).filter(User.username==username).first()
    return User(**schema_user_db(user)) if user else False

def authenticate_user(username,password):
    user = get_user(username)
    if not user: 
        return Response("Not Found", status_code=404)
    
    if not verify_password(password, user.password):
        return Response("Could not validate credentials", status_code=401, headers={"WWW-Authenticate": "Bearer"})
    return user
        
def create_token(data:dict, time_expire:datetime|None=None):
    data_copy = data.copy()
    if time_expire is None:
        expires = datetime.utcnow() + timedelta(minutes=15)
    else:
        expires = datetime.utcnow() + time_expire
    data_copy.update({"exp": expires})
    token_jwt = jwt.encode(data_copy, key=SECRET_KEY, algorithm=ALGORITHM)
    return token_jwt

def decode_current_token(token:str = Depends(oauth2_scheme)):
    # try:
    print(token)
    token_decode = jwt.decode(token, key=SECRET_KEY, algorithms=[ALGORITHM])
    username = token_decode.get("sub")

    if username == None:
        return Response(f"Token failed", status_code=401, headers={"WWW-Authenticate": "Bearer"})
    # except JWTError as e:
    #     return Response(f"Token failed {e}", status_code=401, headers={"WWW-Authenticate": "Bearer"})
    user = get_user(username)
    if not user:
        return Response("Not Found", status_code=404)
    return user

def verify_disabled_user_current(user:User = Depends(decode_current_token)):
    print(user)
    print(type(user))
    if user.disabled:
        return Response("Inactive User..", status_code=400)
    return user


@router.get("/")
async def root_users(user:BUserDb|None):
    usuario = session.query(User).filter(User.username==user.username).first()
    if not usuario:
        return Response("username Not Found", status_code=404)
    is_valid = password_context.verify(user.password, usuario.password)
    if not is_valid:    
        return Response("No Authenticate", status_code=401, headers={"WWW-Authenticate": "Bearer"})
    return usuario
    
@router.get("/me")
async def users(user:User = Depends(verify_disabled_user_current)):
    return user


@router.post("/token")
async def login(form_data: OAuth2PasswordRequestForm = Depends()):
    user = authenticate_user(form_data.username, form_data.password)
    access_token_expire = timedelta(minutes=30)
    access_token_jwt = create_token({"sub": user.username}, access_token_expire, ) 
    return{
            "access_token": access_token_jwt,
            "token_type": "bearer" 
          }
    

#  git@github.com:aliskairraul/Preguntando-a-Colab.git   


        

