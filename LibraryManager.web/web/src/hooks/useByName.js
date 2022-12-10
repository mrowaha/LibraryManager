import {useState, useEffect} from 'react';


function useByName (byName, context) {

    /**
     * Setting name to an object and not to a string
     * This ensures that the search with an empty name field is still fetched
     * Example bug prevention? Switching from a valid Genre to none. In both cases
     * byName stays empty "". But through immutability, the name object is changed 
     */
    const [name, setName] = useState({
        value : byName
    });

    useEffect(() => {
        let fetchUrl = `http://localhost:8080/api/${context.type}`;

        if (name.value !== "") {
            const temp = name.value.replace(" ", "%20")
            fetchUrl = `http://localhost:8080/api/${context.type}?name=${temp}`; 
        }
        fetch(fetchUrl)
            .then(response => {
                return response.json();
            })
            .then(data => {
                context.setByName(data);
            })
            .catch(err => {
                console.log(err);
            })


    }, [name])

    return setName;
}


export default useByName;