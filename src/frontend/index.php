<?php
// CẤU HÌNH
$baseUrl = "http://localhost:8080/api";
$currentLang = isset($_GET['lang']) ? $_GET['lang'] : 'EN';
$currentCat  = isset($_GET['cat']) ? $_GET['cat'] : null;

// HÀM GỌI API (GET)
function fetchApiData($url) {
    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $response = curl_exec($ch);
    curl_close($ch);
    return json_decode($response, true);
}

// HÀM GỌI API (POST - Tạo mới)
function postApiData($url, $data) {
    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
    curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);
    $response = curl_exec($ch);
    curl_close($ch);
}

// --- XỬ LÝ FORM SUBMIT ---
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['action']) && $_POST['action'] == 'create') {
    $newData = [
        'nameEN' => $_POST['nameEN'],
        'descriptionEN' => $_POST['descEN'],
        'nameVI' => $_POST['nameVI'],
        'descriptionVI' => $_POST['descVI'],
        'price' => $_POST['price'],
        'categoryId' => $_POST['categoryId']
    ];

    postApiData("$baseUrl/products", $newData);

    // Refresh trang để thấy kết quả
    header("Location: ?lang=$currentLang&cat=$currentCat");
    exit;
}

// LẤY DỮ LIỆU HIỂN THỊ
$categories = fetchApiData("$baseUrl/categories?lang=$currentLang");
$productUrl = "$baseUrl/products?lang=$currentLang" . ($currentCat ? "&cat=$currentCat" : "");
$products = fetchApiData($productUrl);

function formatMoney($amount, $lang) {
    if ($lang == 'VI') return number_format($amount * 25000, 0, ',', '.') . ' đ';
    return '$' . number_format($amount, 2);
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Shop</title>
    <style>
        /* GIỮ LẠI STYLE CŨ VÀ THÊM CÁI MỚI DƯỚI ĐÂY */
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f4f9; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .lang-btn { padding: 6px 12px; text-decoration: none; border-radius: 4px; font-weight: bold; margin-left: 5px; font-size: 0.9em; }
        .lang-btn.active { background-color: #007bff; color: white; }
        .lang-btn.inactive { background-color: #e9ecef; color: #333; }

        .category-filter { display: flex; gap: 10px; margin-bottom: 30px; padding-bottom: 15px; border-bottom: 2px solid #e9ecef; overflow-x: auto; }
        .filter-btn { padding: 8px 20px; border-radius: 20px; text-decoration: none; font-weight: 600; transition: all 0.2s; white-space: nowrap; }
        .filter-btn.active { background-color: #343a40; color: white; }
        .filter-btn.inactive { background-color: white; color: #666; border: 1px solid #ddd; }

        .product-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 20px; }
        .card { background: white; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); display: flex; flex-direction: column; overflow: hidden; }
        .card-img { height: 150px; background-color: #e9ecef; display: flex; align-items: center; justify-content: center; font-size: 50px; }
        .card-body { padding: 15px; flex-grow: 1; display: flex; flex-direction: column; }
        .category-badge { display: inline-block; background-color: #e2e6ea; color: #495057; font-size: 0.75rem; font-weight: 700; padding: 4px 8px; border-radius: 12px; margin-bottom: 8px; align-self: flex-start; text-transform: uppercase; }
        .card-title { font-size: 1.1em; margin: 0 0 10px; font-weight: bold; }
        .card-desc { font-size: 0.9em; color: #666; margin-bottom: 15px; flex-grow: 1; }
        .card-footer { display: flex; justify-content: space-between; align-items: center; border-top: 1px solid #eee; padding-top: 15px; margin-top: auto; }
        .price { font-size: 1.2em; color: #d9534f; font-weight: bold; }
        .btn-buy { background-color: #28a745; color: white; border: none; padding: 6px 12px; border-radius: 4px; font-weight: 600; }

        /* --- STYLE CHO MODAL VÀ NÚT ADD --- */
        .btn-add { background-color: #6610f2; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-weight: bold; text-decoration: none; display: inline-block;}
        .btn-add:hover { background-color: #520dc2; }

        .modal { display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); }
        .modal-content { background-color: white; margin: 5% auto; padding: 20px; border-radius: 8px; width: 500px; max-width: 90%; box-shadow: 0 5px 15px rgba(0,0,0,0.3); }
        .close { color: #aaa; float: right; font-size: 28px; font-weight: bold; cursor: pointer; }
        .close:hover { color: black; }

        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: 600; }
        .form-group input, .form-group select, .form-group textarea { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .form-row { display: flex; gap: 10px; }
        .form-row .col { flex: 1; }
        .btn-submit { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; font-size: 1.1em; cursor: pointer; }
        .btn-submit:hover { background-color: #0056b3; }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h1>🛍️ ABC Shop</h1>
        <div>
            <button onclick="openModal()" class="btn-add">+ New Product</button>

            <span style="margin-left: 15px">Lang: </span>
            <a href="?lang=EN&cat=<?php echo $currentCat; ?>" class="lang-btn <?php echo $currentLang == 'EN' ? 'active' : 'inactive'; ?>">EN</a>
            <a href="?lang=VI&cat=<?php echo $currentCat; ?>" class="lang-btn <?php echo $currentLang == 'VI' ? 'active' : 'inactive'; ?>">VI</a>
        </div>
    </div>

    <div class="category-filter">
        <a href="?lang=<?php echo $currentLang; ?>" class="filter-btn <?php echo !$currentCat ? 'active' : 'inactive'; ?>">All</a>
        <?php if ($categories): ?>
            <?php foreach ($categories as $cat): ?>
                <a href="?lang=<?php echo $currentLang; ?>&cat=<?php echo $cat['id']; ?>" class="filter-btn <?php echo $currentCat == $cat['id'] ? 'active' : 'inactive'; ?>">
                    <?php echo htmlspecialchars($cat['name']); ?>
                </a>
            <?php endforeach; ?>
        <?php endif; ?>
    </div>

    <?php if ($products): ?>
        <div class="product-grid">
            <?php foreach ($products as $p): ?>
                <div class="card">
                    <div class="card-img">📦</div>
                    <div class="card-body">
                        <?php if (!empty($p['categoryName'])): ?><span class="category-badge"><?php echo htmlspecialchars($p['categoryName']); ?></span><?php endif; ?>
                        <h3 class="card-title"><?php echo htmlspecialchars($p['name']); ?></h3>
                        <p class="card-desc"><?php echo htmlspecialchars($p['description']); ?></p>
                        <div class="card-footer">
                            <span class="price"><?php echo formatMoney($p['price'], $currentLang); ?></span>
                            <button class="btn-buy">Buy</button>
                        </div>
                    </div>
                </div>
            <?php endforeach; ?>
        </div>
    <?php else: ?>
        <p style="text-align:center">No products found or Backend error.</p>
    <?php endif; ?>
</div>

<div id="addProductModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2 style="margin-top:0">Add New Product</h2>

        <form method="POST">
            <input type="hidden" name="action" value="create">

            <div class="form-row">
                <div class="col form-group">
                    <label>Price ($)</label>
                    <input type="number" step="0.01" name="price" required>
                </div>
                <div class="col form-group">
                    <label>Category</label>
                    <select name="categoryId">
                        <?php if ($categories): foreach ($categories as $cat): ?>
                            <option value="<?php echo $cat['id']; ?>"><?php echo $cat['name']; ?></option>
                        <?php endforeach; endif; ?>
                    </select>
                </div>
            </div>

            <div style="background: #f8f9fa; padding: 10px; border-radius: 5px; margin-bottom: 10px;">
                <div class="form-group">
                    <label>Name (English)</label>
                    <input type="text" name="nameEN" placeholder="e.g. Gaming Keyboard" required>
                </div>
                <div class="form-group">
                    <label>Description (English)</label>
                    <textarea name="descEN" rows="2" required></textarea>
                </div>
            </div>

            <div style="background: #eef2f7; padding: 10px; border-radius: 5px; margin-bottom: 15px;">
                <div class="form-group">
                    <label>Tên (Tiếng Việt)</label>
                    <input type="text" name="nameVI" placeholder="VD: Bàn phím chơi game" required>
                </div>
                <div class="form-group">
                    <label>Mô tả (Tiếng Việt)</label>
                    <textarea name="descVI" rows="2" required></textarea>
                </div>
            </div>

            <button type="submit" class="btn-submit">Save Product</button>
        </form>
    </div>
</div>

<script>
    var modal = document.getElementById("addProductModal");
    function openModal() { modal.style.display = "block"; }
    function closeModal() { modal.style.display = "none"; }
    window.onclick = function(event) {
        if (event.target == modal) { closeModal(); }
    }
</script>

</body>
</html>