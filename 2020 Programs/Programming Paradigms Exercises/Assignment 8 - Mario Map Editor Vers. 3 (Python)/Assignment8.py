import pygame
import time

from pygame.locals import*
from time import sleep

class Sprite():
    def __init__(self, x, y):
        self.x = x
        self.y = y

class Mario(Sprite):
    def __init__(self, x, y):
        self.mario_images = []
        self.loadMarioImages()
        
        self.width = 60
        self.height = 95
        self.x = x
        self.y = y
        
        self.marioImageNum = 0
        self.marioOffset = 100
        self.flip = False
        
        self.px = x
        self.py = y
        
        self.vert_vel = 9.81
        self.air_time = 0

    def loadMarioImages(self):
        #Load Array Of Mario Images
        self.mario_images.append(pygame.image.load("mario1.png"))
        self.mario_images.append(pygame.image.load("mario2.png"))
        self.mario_images.append(pygame.image.load("mario3.png"))
        self.mario_images.append(pygame.image.load("mario4.png"))
        self.mario_images.append(pygame.image.load("mario5.png"))

    def draw(self, screen):
        #Draw Mario
        self.screen = screen
        self.screen.blit(self.mario_images[self.marioImageNum], (self.x - self.x + self.marioOffset, self.y) )

    def savePrevPos(self):
        self.px = self.x
        self.py = self.y

    def update(self):
        #Set Normal Gravity
        self.vert_vel += 5.0
        self.y += self.vert_vel
        self.air_time += 1
        
        #Enable Ground
        if(self.y > 400 - self.height):
            self.vert_vel = 0.0
            self.y = 400 - self.height
            self.air_time = 0
        
        #Enable Ceiling
        if(self.y < 0):
            self.y = 0

    def updateAnimation(self):
        self.marioImageNum += 1
        if(self.marioImageNum > 4):
            self.marioImageNum = 0

    def marioJump(self):
        self.vert_vel = -30.0

    def getOutOfTube(self, tube):
        #Jump Left Out Of Tube
        if( (self.x + self.width >= tube.x) and (self.px + self.width <= tube.x) ):
            self.x = tube.x - self.width;
        #Jump Right Out Of Tube
        if( (self.x <= tube.x + tube.width) and (self.px >= tube.x + tube.width) ):
            self.x = tube.x + tube.width;
        #Jump Up Out Of Tube
        if( (self.y + self.height >= tube.y) and (self.py + self.height <= tube.y) ):
            self.y = tube.y - self.height;
            #Allows Mario To Better Jump Off Of Top Of Tube
            self.air_time = 0;
        #Jump Down Out Of Tube
        if( (self.y <= tube.y + tube.height) and (self.py >= tube.y + tube.height) ):
            self.y = tube.y + tube.height;

class Tube(Sprite):
    def __init__(self, x, y, model):
            self.tube_image = None #None replaces Null
            self.loadTubeImage()
            
            self.width = 55
            self.height = 400
            self.x = x
            self.y = y
            self.model = model

    def loadTubeImage(self):
        if(self.tube_image == None):
            self.tube_image = pygame.image.load("tube.png")

    def draw(self, screen):
        self.screen = screen
        self.screen.blit(self.tube_image, (self.x - self.model.mario.x + self.model.mario.marioOffset, self.y) )

    def update(self):
        pass

class Goomba(Sprite):
    def __init__(self, x, y, model):
        self.goomba_image = None
        self.loadGoombaImage()
        
        self.width = 40
        self.height = 48
        self.x = x
        self.y = y
        self.model = model
        
        self.px = self.x
        self.py = self.y
        self.vert_vel = 0.0
        self.direction = 1
        self.onFire = False
        self.timer = 15

    def loadGoombaImage(self):
        if(self.goomba_image == None):
            self.goomba_image = pygame.image.load("goomba.png")

    def loadGoombaFireImage(self):
        if(self.goomba_image != None):
            self.goomba_image = pygame.image.load("goomba_fire.png")

    def draw(self, screen):
        self.screen = screen
        self.screen.blit(self.goomba_image, (self.x - self.model.mario.x + self.model.mario.marioOffset, self.y) )

    def savePrevPos(self):
        self.px = self.x
        self.py = self.y

    def update(self):
        self.savePrevPos()
        #Set Normal Gravity
        self.vert_vel += 5.0
        self.y += self.vert_vel
        
        #Enable Ground
        if(self.y > 400 - self.height):
            self.vert_vel = 0.0
            self.y = 400 - self.height
        
        #Movement
        self.x += 2 * self.direction
        
        #Check Fire Status
        if(self.onFire == True):
            self.loadGoombaFireImage()

    def getOutOfTube(self, tube):
        #Move Left Out Of Tube
        if( (self.x + self.width >= tube.x) and (self.px + self.width <= tube.x) ):
            self.x = tube.x - self.width;
            self.direction = -1
        #Move Right Out Of Tube
        if( (self.x <= tube.x + tube.width) and (self.px >= tube.x + tube.width) ):
            self.x = tube.x + tube.width;
            self.direction = 1
        #Move Up Out Of Tube
        if( (self.y + self.height >= tube.y) and (self.py + self.height <= tube.y) ):
            self.y = tube.y - self.height;
        #Move Down Out Of Tube
        if( (self.y <= tube.y + tube.height) and (self.py >= tube.y + tube.height) ):
            self.y = tube.y + tube.height;

class Fireball(Sprite):
    def __init__(self, x, y, direction, model):
        self.fireball_image = None
        self.loadFireballImage()
        
        self.width = 25
        self.height = 25
        self.x = x
        self.y = y
        self.direction = direction
        self.model = model
        
        self.vert_vel = 9.81
        self.offScreen = False

    def loadFireballImage(self):
        if(self.fireball_image == None):
            self.fireball_image = pygame.image.load("fireball.png")

    def draw(self, screen):
        self.screen = screen
        self.screen.blit(self.fireball_image , (self.x - self.model.mario.x + self.model.mario.marioOffset, self.y) )

    def update(self):
        #Set Normal Gravity
        self.vert_vel += 5.0
        self.y += self.vert_vel
        
        #Enable Ground
        if(self.y > 400 - self.height):
            self.vert_vel = -30.0
            self.y = 400 - self.height
        
        #Enable Ceiling
        if(self.y < 0):
            self.y = 0
        
        #Movement
        self.x += 5 * self.direction
        
        #Checks If Fireballs Are Off Screen
        if((self.x - self.model.mario.x + self.model.mario.marioOffset > 700) or (self.x - self.model.mario.x + self.model.mario.marioOffset < 0)):
            self.offScreen = True

class Model():
    def __init__(self):
        self.sprites = []
        self.mario = Mario(200, 50)
        self.sprites.append(self.mario)
        self.sprites.append(Tube(286, 335, self))
        self.sprites.append(Tube(111, 293, self))
        self.sprites.append(Tube(464, 212, self))
        self.sprites.append(Tube(579, 303, self))
        self.sprites.append(Goomba(345, 352, self))
        self.sprites.append(Goomba(367, 352, self))
        self.sprites.append(Goomba(527, 352, self))

    def addFireballModel(self, x, y, direction):
        self.fireball = Fireball(x, y, direction, self)
        self.sprites.append(self.fireball)

    def update(self):
        self.deleteArray = []
        for i in range(len(self.sprites)):
            #print(i)
            self.sprites[i].update()
            #Checks If Mario Collides With Tubes
            if(isinstance(self.sprites[i], Tube)):
                self.t = self.sprites[i]
                #Checks Collision of Two Sprites
                if(self.modelCollision(self.mario, self.t) == True):
                    self.mario.getOutOfTube(self.t)
            
            #Checks If Goombas Collides With Tubes
            if(isinstance(self.sprites[i], Goomba)):
                self.g = self.sprites[i]
                for i2 in range(len(self.sprites)):
                    if(isinstance(self.sprites[i2], Tube)):
                        self.t = self.sprites[i2]
                        #Checks Collision Of Two Sprites
                        if(self.modelCollision(self.g, self.t) == True):
                            self.g.getOutOfTube(self.t)
            
            #Checks If Fireball Collides With Goomba
            if(isinstance(self.sprites[i], Fireball)):
                self.fb = self.sprites[i]
                for i3 in range(len(self.sprites)):
                    if(isinstance(self.sprites[i3], Goomba)):
                        self.g = self.sprites[i3]
                        #Checks Collision of Two Sprites
                        if(self.modelCollision(self.fb, self.g) == True):
                            self.g.onFire = True
                            self.g.timer -= 1
                            if(self.g.timer == 0):
                                self.deleteArray.append(i3)
                #Removes Fireballs If Fireballs Are Off Screen
                if(self.fb.offScreen == True):
                    self.deleteArray.append(i)
        if(len(self.deleteArray) > 0):
            for i4 in range(len(self.deleteArray)):
                del self.sprites[self.deleteArray[i4]]
        for i5 in range(len(self.deleteArray)):
            del self.deleteArray[i5]

    def modelCollision(self, a, b):
        #Sprite a Left of Sprite b
        if(a.x + self.mario.width < b.x):
            return False;
        #Sprite a Right of Sprite b
        if(a.x > b.x + b.width):
            return False;
        #Sprite a Above Sprite b
        if(a.y + self.mario.height < b.y):
            return False;
        #Sprite a Below Sprite b
        if(a.y > b.y + b.height):
            return False;
        return True;

class View():
    def __init__(self, model):
        self.model = model
        screen_size = (700, 500)
        self.screen = pygame.display.set_mode(screen_size, 32)

    def update(self):
        #Background
        self.screen.fill([128, 191, 255])
        
        #Ground
        pygame.draw.rect(self.screen, [172, 57, 57], [0, 400, 700, 100])
        
        #Draw Sprites
        for i in range(len(self.model.sprites)):
            self.model.sprites[i].draw(self.screen)
        
        pygame.display.flip()

class Controller():
    def __init__(self, model, view):
        self.model = model
        self.view = view
        self.keep_going = True

    def update(self):
        for event in pygame.event.get():
            if event.type == QUIT:
                self.keep_going = False
            elif event.type == KEYDOWN:
                if event.key == K_ESCAPE:
                    self.keep_going = False
        self.model.mario.savePrevPos()
        keys = pygame.key.get_pressed()
        #right arrow
        if keys[K_RIGHT]:
            self.model.mario.flip = False
            self.model.mario.x += 5
            self.model.mario.updateAnimation()
        
        #left arrow
        if keys[K_LEFT]:
            self.model.mario.flip = True
            self.model.mario.x -= 5
            self.model.mario.updateAnimation()
        
        #spacebar
        if keys[K_SPACE]:
            if(self.model.mario.air_time < 5):
                self.model.mario.marioJump()
        
        #ctrl
        if keys[K_LCTRL or K_RCTRL]:
            if(self.model.mario.flip == False):
                self.model.addFireballModel(self.model.mario.x + self.model.mario.width/2, self.model.mario.y + self.model.mario.height/2, 1)
            else:
                self.model.addFireballModel(self.model.mario.x + self.model.mario.width/2, self.model.mario.y + self.model.mario.height/2, -1)

print("Use the arrow keys to move. Press Esc to quit.")
pygame.init()
m = Model()
v = View(m)
c = Controller(m, v)
while c.keep_going:
    c.update()
    m.update()
    v.update()
    sleep(0.04)
print("Goodbye")