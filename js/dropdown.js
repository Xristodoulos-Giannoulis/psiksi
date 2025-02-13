// Function to toggle dropdown visibility
function toggleDropdown(dropdownId) {
    var content = document.getElementById(dropdownId);
    content.style.display = (content.style.display === "block") ? "none" : "block";
}

// Attach event listeners to buttons
document.getElementById("sortButton").onclick = function() {
    toggleDropdown("sortDropdown");
};

document.getElementById("sizeButton").onclick = function() {
    toggleDropdown("sizeDropdown");
};

document.getElementById("brandButton").onclick = function() {
    toggleDropdown("brandDropdown");
};

// Close dropdowns if clicked outside
window.onclick = function(event) {
    if (!event.target.matches('.custom-button')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.style.display === "block") {
                openDropdown.style.display = "none";
            }
        }
    }
}

