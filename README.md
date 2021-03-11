Combine request a l'API de github et base de donnees locale

Base dur Cours12

Le Repository se charge de la logique de demande ou sauvegarde des donnees:
- Demande les donnees a la dataBase locale 
- Si un certain delais est passe entre 2 demande de donnees il demande les donnees a l'api github (grace a retrofit) et les sauvegarde dans la database locale

DataResponse Class
- Permet d'envelopper tout type d'objet (DataResponse<T> ou T peut etre tout type d'objet) et comporte un second parametre RequestType (enum declare dasn DataResponse class)

MediatorLiveData
- s'observe comme tout liveData
- Permet d'ajouter plusieurs source a son instance et donc de combiner plusieurs live data
- Concretement permet d'observer un liveData depuis un repository ou un viewModel et de recuperer sa valeur que l'on peut poster entant que valeur du mediatorLiveData 
- Ici nous observons le liveData retourne par le DAO getAllUsers() et inserons les users dasn un objet DataResponse et ainsi pouvons egalement changer la valeur de requestStatus et poster la nouvelle valeur de dataResponse avec notre mediatorLiveData qui est observe dans UsersFragment
