<?php
$iconeMap = [
    'imgTelescopio' => 'Imagens_Mobile_HIO/imgtelescopio.png',
    'imgMacacaIndo' => 'Imagens_Mobile_HIO/imgmacacaindo.png',
    'imgComputador' => 'Imagens_Mobile_HIO/imgcomputador.png',
    'imgOperacoesBasicas' => 'Imagens_Mobile_HIO/imgoperacoesbasicas.png',
    'imgPapiro' => 'Imagens_Mobile_HIO/imgpapiro.png',
    'imgTuboDeEnsaio' => 'Imagens_Mobile_HIO/imgtubodeensaio.png',
    'imgDna' => 'Imagens_Mobile_HIO/imgdna.png',
    'imgAtomo' => 'Imagens_Mobile_HIO/imgatomo.png',
];

$image = $_GET['image'] ?? '';

if (isset($iconeMap[$image])) {
    $filePath = $iconeMap[$image];
    if (file_exists($filePath)) {
        header('Content-Type: ' . mime_content_type($filePath));
        readfile($filePath);
        exit;
    }
}

http_response_code(404);
echo 'Imagem não encontrada.';
?>
