### Créer le jar de l'application ###



http://java.developpez.com/faq/java/?page=execution#creationJar

Copiez le contenu du dossier bin dans un dossier QRCode

Décompresser jdom.jar et placer le "org" a la racine du projet (même niveau que vue model etc ...).

---MANIFEST.MF---

Main-Class: Main

---

(pensez au saut de ligne a la fin)


se placer dans le dossier QRCode
jar cvfm qrcode.jar META-INF/MANIFEST.MF .


copiez le dossier src dans le dossier QRCode. Ne conserver que les fichier .xml et .dtd ( Supprimer tout les .java)

Le jar doit donc toujours être a coté du dossier contenant les fichiers XML.

Pour lancer l'application


java -jar qrcode.jar